package ro.code4.monitorizarevot.db;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import ro.code4.monitorizarevot.constants.FormType;
import ro.code4.monitorizarevot.net.model.*;

public class Data {

    private static final String ID = "id";

    private static final String BRANCH_NUMBER = "branchNumber";

    private static final String COUNTY_CODE = "countyCode";

    private static final String IS_SYNCED = "isSynced";

    private static Data instance;

    private Data() {

    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private static int getNextPrimaryKey(Realm realm, Class realmClass) {
        Number maxPrimaryKeyValue = realm.where(realmClass).max(ID);
        return maxPrimaryKeyValue != null ? maxPrimaryKeyValue.intValue() + 1 : 0;
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
        BranchDetails result = results.size() > 0 ? realm.copyFromRealm(results.get(0)) : null;
        realm.close();
        return result;
    }

    public Form getFirstForm() {
        return getForm(FormType.FIRST);
    }

    public Form getSecondForm() {
        return getForm(FormType.SECOND);
    }

    public Form getThirdForm() {
        return getForm(FormType.THIRD);
    }

    public Form getForm(String formId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Form> results = realm
                .where(Form.class)
                .equalTo(ID, formId)
                .findAll();
        Form result = results.size() > 0 ? realm.copyFromRealm(results.get(0)) : null;
        realm.close();
        return result;
    }

    public Version getFormVersion() {
        RealmResults<Version> queryResult = Realm.getDefaultInstance()
                                                 .where(Version.class)
                                                 .findAll();
        return queryResult.size() > 0 ? queryResult.first() : null;
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
                .equalTo(ID, questionId)
                .findFirst();
        Question question = realm.copyFromRealm(result);
        realm.close();
        return question;
    }

    public void saveAnswerResponse(BranchQuestionAnswer branchQuestionAnswer) {
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

    public void saveFormsVersion(Version version) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(version);
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
                .equalTo(ID, questionId)
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
                                          .equalTo(ID, note.getId())
                                          .findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public BranchQuestionAnswer getCityBranch(Integer questionId) {
        Realm realm = Realm.getDefaultInstance();
        BranchQuestionAnswer result = realm
                .where(BranchQuestionAnswer.class)
                .equalTo(ID, getCityBranchId(questionId))
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
                .equalTo(ID, getCityBranchId(questionId))
                .findAll();
        List<BranchQuestionAnswer> branchQuestionAnswers = realm.copyFromRealm(result);
        realm.close();
        return branchQuestionAnswers;
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
