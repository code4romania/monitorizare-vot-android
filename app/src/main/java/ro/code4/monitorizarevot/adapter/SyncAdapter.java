package ro.code4.monitorizarevot.adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro.code4.monitorizarevot.constants.FormType;
import ro.code4.monitorizarevot.constants.Sync;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.*;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.observable.ObservableListener;
import ro.code4.monitorizarevot.util.FormUtils;
import ro.code4.monitorizarevot.util.Logify;

import static ro.code4.monitorizarevot.util.AuthUtils.createSyncAccount;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        init();
    }

    public static void requestSync(Context context) {
        Account account = createSyncAccount(context);
        ContentResolver.setSyncAutomatically(account, Sync.AUTHORITY, true);
        ContentResolver.requestSync(account, Sync.AUTHORITY, getBundle(false));
    }

    public static void requestUploadSync(Context context) {
        if (ContentResolver.getMasterSyncAutomatically()) {
            Account account = createSyncAccount(context);
            ContentResolver.setSyncAutomatically(account, Sync.AUTHORITY, true);
            ContentResolver.requestSync(account, Sync.AUTHORITY, getBundle(true));
        }
    }

    @NonNull
    private static Bundle getBundle(boolean isUpload) {
        Bundle extras = new Bundle();
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, isUpload);
        return extras;
    }

    private void init() {

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Logify.d("SyncAdapter", "performing sync");

        if (extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false)) {
            doUpload();
        } else {
            doSync();
        }
    }

    private void doUpload() {
        postBranchDetails();
        postQuestionAnswers();
        postNotes();
    }

    private void doSync() {
        doUpload();
        getFormsDefinition();
    }

    private void postBranchDetails() {
        List<BranchDetails> branchDetailsList = Data.getInstance().getUnsyncedList(BranchDetails.class);
        for (BranchDetails branchDetails : branchDetailsList) {
            try {
                NetworkService.postBranchDetails(branchDetails);
                Data.getInstance().markSynced(branchDetails);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void postQuestionAnswers() {
        try {
            List<QuestionAnswer> questionAnswers = new ArrayList<>();
            getAnswersFromForm(Data.getInstance().getFirstForm(), questionAnswers);
            getAnswersFromForm(Data.getInstance().getSecondForm(), questionAnswers);
            getAnswersFromForm(Data.getInstance().getThirdForm(), questionAnswers);
            NetworkService.postQuestionAnswer(new ResponseAnswerContainer(questionAnswers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postNotes() {
        List<Note> notes = Data.getInstance().getNotes();
        for (Note note : notes) {
            try {
                NetworkService.postNote(note);
                Data.getInstance().deleteNote(note);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAnswersFromForm(Form form, List<QuestionAnswer> questionAnswers) {
        if (form != null) {
            List<Question> questionList = FormUtils.getAllQuestions(form.getId());
            for (Question question : questionList) {
                if (!question.isSynced()) {
                    for (BranchQuestionAnswer branchQuestionAnswer : Data.getInstance().getCityBranchPerQuestion(question.getId())) {
                        QuestionAnswer questionAnswer = new QuestionAnswer(branchQuestionAnswer, form.getId());
                        questionAnswers.add(questionAnswer);
                    }
                }
            }
        }
    }

    private void getFormsDefinition() {
        try {
            VersionResponse versionResponse = NetworkService.doGetFormVersion();
            Version existingVersion = Data.getInstance().getFormVersion();
            if (!versionsEqual(existingVersion, versionResponse.getVersion())) {
                Data.getInstance().deleteAnswersAndNotes();
                getForms(versionResponse.getVersion());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean versionsEqual(Version before, Version current) {
        return (before != null)
                && before.getA().equals(current.getA())
                && before.getB().equals(current.getB())
                && before.getC().equals(current.getC());
    }

    private void getForms(Version version) {
        FormDefinitionSubscriber subscriber = new FormDefinitionSubscriber(version, 3);
        NetworkService.doGetForm(FormType.FIRST).startRequest(subscriber);
        NetworkService.doGetForm(FormType.SECOND).startRequest(subscriber);
        NetworkService.doGetForm(FormType.THIRD).startRequest(subscriber);
    }

    private class FormDefinitionSubscriber extends ObservableListener<Boolean> {

        private final Version version;

        private final int numberOfRequests;

        private int successCount = 0;

        FormDefinitionSubscriber(Version version, int numberOfRequests) {
            this.version = version;
            this.numberOfRequests = numberOfRequests;
        }

        @Override
        public void onSuccess() {
            if (successCount == numberOfRequests) {
                Data.getInstance().saveFormsVersion(version);
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Boolean aBoolean) {
            successCount++;
        }
    }
}
