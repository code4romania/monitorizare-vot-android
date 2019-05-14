package ro.code4.monitorizarevot.db;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import ro.code4.monitorizarevot.net.model.*;
import ro.code4.monitorizarevot.util.FormUtils;

public class Data {
    private static final String AUTO_INCREMENT_PRIMARY_KEY = "id";
    private static final String BRANCH_ID = "id";
    private static final String BRANCH_NUMBER = "branchNumber";
    private static final String COUNTY_CODE = "countyCode";
    private static final String FORM_ID = "id";
    private static final String IS_SYNCED = "isSynced";
    private static final String NOTE_ID = "id";
    private static final String QUESTION_ID = "id"; // TODO why not keep all Primary Keys as "id" and delete it from here

    private static Data instance;

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private static int getNextPrimaryKey(Realm realm, Class realmClass) {
        Number maxPrimaryKeyValue = realm.where(realmClass).max(AUTO_INCREMENT_PRIMARY_KEY);
        return maxPrimaryKeyValue != null ? maxPrimaryKeyValue.intValue() + 1 : 0;
    }

    private Data() {

    }

    public void deleteAnswersAndNotes() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(BranchQuestionAnswer.class);
        realm.delete(Note.class);
        realm.commitTransaction();
        realm.close();
    }

    public BranchDetails getCurrentBranchDetails() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<BranchDetails> results = realm
                .where(BranchDetails.class)
                .equalTo(COUNTY_CODE, Preferences.getCountyCode())
                .equalTo(BRANCH_NUMBER, Preferences.getBranchNumber())
                .findAll();
        BranchDetails result = !results.isEmpty() ? realm.copyFromRealm(results.first()) : null;
        realm.close();
        return result;
    }

    private List<Form> getAllForms() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Form> formsRealmResults = realm
                .where(Form.class)
                .findAll();
        List<Form> forms = realm.copyFromRealm(formsRealmResults);
        realm.close();
        return forms;
    }

    public Form getForm(String formId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Form> results = realm
                .where(Form.class)
                .equalTo(FORM_ID, formId)
                .findAll();
        Form result = !results.isEmpty() ? realm.copyFromRealm(results.first()) : null;
        realm.close();
        return result;
    }

    public List<Version> getFormVersions() {
        return Realm.getDefaultInstance()
                .where(Version.class)
                .findAll();
    }

    public List<FormDetails> getFormDetails() {
        return Realm.getDefaultInstance()
                .where(FormDetails.class)
                .findAll();
    }

    public List<Note> getNotes() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Note> result = realm
                .where(Note.class)
                .findAll();
        List<Note> notes = realm.copyFromRealm(result);
        realm.close();
        return notes;
    }

    public Question getQuestion(Integer questionId) {
        Realm realm = Realm.getDefaultInstance();
        Question result = realm
                .where(Question.class)
                .equalTo(QUESTION_ID, questionId)
                .findFirst();
        Question question = realm.copyFromRealm(result);
        realm.close();
        return question;
    }

    public void saveAnswerResponse(BranchQuestionAnswer branchQuestionAnswer) {
        Log.d(Data.class.getName(), "Saving new answer for question " +
                branchQuestionAnswer.getQuestionId() + " with " +
                branchQuestionAnswer.getAnswers().size() + " answers!");
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(branchQuestionAnswer);
        realm.commitTransaction();
        realm.close();
    }

    public void saveBranchDetails(BranchDetails branchDetails) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(branchDetails);
        realm.commitTransaction();
        realm.close();
    }

    public void saveFormDefinition(String formId, List<Section> sections) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Form form = new Form();
        form.setId(formId);
        form.setSections(new RealmList<Section>());
        form.getSections().addAll(sections);
        realm.copyToRealmOrUpdate(form);
        realm.commitTransaction();
        realm.close();
    }

    public void saveFormsVersion(List<FormDetails> versions) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(versions);
        realm.commitTransaction();
        realm.close();
    }

    public Note saveNote(String uriPath, String description, Integer questionId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Note note = realm.createObject(Note.class, getNextPrimaryKey(realm, Note.class));
        note.setUriPath(uriPath);
        note.setDescription(description);
        note.setQuestionId(questionId);
        Note copyNote = realm.copyFromRealm(note);
        realm.commitTransaction();
        realm.close();
        return copyNote;
    }


    public void updateQuestionStatus(Integer questionId) {
        Realm realm = Realm.getDefaultInstance();
        Question question = realm
                .where(Question.class)
                .equalTo(QUESTION_ID, questionId)
                .findFirst();

        realm.beginTransaction();
        question.setSynced(true);
        realm.copyToRealmOrUpdate(question);
        realm.commitTransaction();

        realm.close();
    }

    public void deleteNote(Note note) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Note> results = realm.where(Note.class)
                .equalTo(NOTE_ID, note.getId())
                .findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public BranchQuestionAnswer getCityBranch(Integer questionId) {
        Realm realm = Realm.getDefaultInstance();
        BranchQuestionAnswer result = realm
                .where(BranchQuestionAnswer.class)
                .equalTo(BRANCH_ID, getCityBranchId(questionId))
                .findFirst();
        BranchQuestionAnswer branchQuestionAnswer = result != null ? realm.copyFromRealm(result) : null;
        realm.close();
        return branchQuestionAnswer;
    }

    @NonNull
    private String getCityBranchId(Integer questionId) {
        return Preferences.getCountyCode() +
                String.valueOf(Preferences.getBranchNumber()) +
                String.valueOf(questionId);
    }

    public List<BranchQuestionAnswer> getCityBranchPerQuestion(Integer questionId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<BranchQuestionAnswer> result = realm
                .where(BranchQuestionAnswer.class)
                .equalTo(BRANCH_ID, getCityBranchId(questionId))
                .findAll();
        List<BranchQuestionAnswer> branchQuestionAnswers = realm.copyFromRealm(result);
        realm.close();
        return branchQuestionAnswers;
    }

    public List<QuestionAnswer> getUnsyncedQuestionAnswersFromAllForms() {
        List<QuestionAnswer> result = new ArrayList<>();
        for (Form form: getAllForms()) {
            result.addAll(getUnsyncedQuestionAnswersFromForm(form));
        }

        return result;
    }

    /**
     *
     * @param formId
     * @return
     */
    public List<QuestionAnswer> getUnsyncedQuestionAnswersFromForm(String formId) {
        List<Question> questionList = FormUtils.getAllQuestions(formId);
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        for (Question question : questionList) {
            // skip if already synced
            if (question.isSynced()) {
                continue ;
            }
            questionAnswerList.addAll(getAnswersForQuestionInForm(question, formId));
        }
        return questionAnswerList;
    }

    private List<QuestionAnswer> getUnsyncedQuestionAnswersFromForm(@NonNull Form form) {
        return getUnsyncedQuestionAnswersFromForm(form.getId());
    }

    private List<QuestionAnswer> getAnswersForQuestionInForm(Question question, String formId) {
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        for (BranchQuestionAnswer branchQuestionAnswer : Data.getInstance().getCityBranchPerQuestion(question.getId())) {
            QuestionAnswer questionAnswer = new QuestionAnswer(branchQuestionAnswer, formId);
            questionAnswerList.add(questionAnswer);
        }
        return questionAnswerList;
    }

    public <T extends Syncable & RealmModel> List<T> getUnsyncedList(Class<T> objectClass) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<T> results = realm
                .where(objectClass)
                .equalTo(IS_SYNCED, false)
                .findAll();
        List<T> resultList = realm.copyFromRealm(results);
        realm.close();
        return resultList;
    }

    public <T extends Syncable & RealmModel> void markSynced(T object) {
        markSyncable(object, true);
    }

    public <T extends Syncable & RealmModel> void markUnsynced(T object) {
        markSyncable(object, false);
    }

    private <T extends Syncable & RealmModel> void markSyncable(T object, boolean isSynced) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        object.setSynced(isSynced);
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        realm.close();
    }
}
