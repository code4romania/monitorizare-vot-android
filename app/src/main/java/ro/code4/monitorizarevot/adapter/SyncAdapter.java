package ro.code4.monitorizarevot.adapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro.code4.monitorizarevot.constants.Sync;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.BranchQuestionAnswer;
import ro.code4.monitorizarevot.net.model.Form;
import ro.code4.monitorizarevot.net.model.Note;
import ro.code4.monitorizarevot.net.model.Question;
import ro.code4.monitorizarevot.net.model.QuestionAnswer;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Version;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.util.FormUtils;
import ro.code4.monitorizarevot.util.Logify;

import static ro.code4.monitorizarevot.constants.Sync.ACCOUNT;
import static ro.code4.monitorizarevot.constants.Sync.ACCOUNT_TYPE;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init(context);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Logify.d("SyncAdapter", "performing sync");

        try {
            if (extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false)) {
                doUpload();
            } else {
                doSync();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doUpload() throws IOException {
        postBranchDetails();
        postQuestionAnswers();
        postNotes();
    }

    private void doSync() throws IOException {
        doUpload();
        getFormsDefinition();
    }

    private void postBranchDetails() throws IOException {
        List<BranchDetails> branchDetailsList = Data.getInstance().getUnsyncedList(BranchDetails.class);
        for (BranchDetails branchDetails : branchDetailsList) {
            NetworkService.postBranchDetails(branchDetails);
            Data.getInstance().markSynced(branchDetails);   //TODO move this to success if throws is removed
        }
    }

    private void postQuestionAnswers() throws IOException {
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        getAnswersFromForm(Data.getInstance().getFormA(), questionAnswers);
        getAnswersFromForm(Data.getInstance().getFormB(), questionAnswers);
        getAnswersFromForm(Data.getInstance().getFormC(), questionAnswers);
        NetworkService.postQuestionAnswer(new ResponseAnswerContainer(questionAnswers));
    }

    private void postNotes() throws IOException {
        List<Note> notes = Data.getInstance().getNotes();
        for (Note note : notes) {
            NetworkService.postNote(note);
            Data.getInstance().deleteNote(note);    //TODO move this to success if throws is removed
        }
    }

    private void getAnswersFromForm(Form form, List<QuestionAnswer> questionAnswers) {
        if(form != null){
            List<Question> questionList = FormUtils.getAllQuestions(form.getId());
            for (Question question : questionList) {
                if(!question.isSynced()){
                    for (BranchQuestionAnswer branchQuestionAnswer : Data.getInstance().getCityBranchPerQuestion(question.getId())) {
                        QuestionAnswer questionAnswer = new QuestionAnswer(branchQuestionAnswer, form.getId());
                        questionAnswers.add(questionAnswer);
                    }
                }
            }
        }
    }

    private void getFormsDefinition() throws IOException {
        VersionResponse versionResponse = NetworkService.doGetFormVersion();
        Version existingVersion = Data.getInstance().getFormVersion();
        if(!versionsEqual(existingVersion, versionResponse.getVersion())) {
            Data.getInstance().saveFormsVersion(versionResponse.getVersion());
            // TODO clear any saved question when definition changes
            getForms();
        }
    }

    private boolean versionsEqual(Version before, Version current) {
        return (before != null)
                && before.getA().equals(current.getA())
                && before.getB().equals(current.getB())
                && before.getC().equals(current.getC());
    }

    private void getForms() throws IOException {
        NetworkService.doGetForm("A");
        NetworkService.doGetForm("B");
        NetworkService.doGetForm("C");
    }

    public static void requestSync(Context context) {
        ContentResolver.requestSync(createSyncAccount(context), Sync.AUTHORITY, getBundle(false));
    }

    public static void requestUploadSync(Context context) {
        if (ContentResolver.getMasterSyncAutomatically()) {
            ContentResolver.requestSync(createSyncAccount(context), Sync.AUTHORITY, getBundle(true));
        }
    }

    @NonNull
    private static Bundle getBundle(boolean isUpload) {
        Bundle extras = new Bundle();
        extras.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true);
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, isUpload);
        return extras;
    }

    private static Account createSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        }
        try {
            return accountManager.getAccountsByType(ACCOUNT_TYPE)[0];
        } catch (SecurityException e) {
            Toast.makeText(context, "Eroare permisiune " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
