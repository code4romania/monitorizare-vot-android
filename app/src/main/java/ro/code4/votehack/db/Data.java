package ro.code4.votehack.db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import ro.code4.votehack.net.model.Form;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.net.model.Version;
import ro.code4.votehack.net.model.response.ResponseAnswer;

public class Data {
    private static Data instance;
    private Realm realm;

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private Data() {

    }

    public Form getFormA() {
        return getForm("A");
    }

    public Form getFormB() {
        return getForm("B");
    }

    public Form getFormC() {
        return getForm("C");
    }

    public Form getForm(String formId) {
        realm = Realm.getDefaultInstance();
        RealmResults<Form> results = realm
                .where(Form.class)
                .equalTo("id", formId)
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

    public List<Question> getUnSyncedQuestions(){
        realm = Realm.getDefaultInstance();
        RealmResults<Question> questions = realm.where(Question.class)
                .equalTo("isSynced", false)
                .findAll();
        List<Question> unSyncedQuestions = realm.copyFromRealm(questions);
        realm.close();
        return unSyncedQuestions;
    }

    public Question getQuestion(Integer questionId) {
        realm = Realm.getDefaultInstance();
        Question result = realm
                .where(Question.class)
                .equalTo("idIntrebare", questionId)
                .findFirst();
        Question question = realm.copyFromRealm(result);
        realm.close();
        return question;
    }

    public void saveAnswerResponse(Question question, List<ResponseAnswer> responseAnswer) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        question.setRaspunsuriIntrebare(responseAnswer);
        realm.copyToRealmOrUpdate(question);
        realm.commitTransaction();
        realm.close();
    }

    public void saveFormDefinition(String formId, List<Section> sections) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Form form = realm.createObject(Form.class, formId);
        form.setSections(new RealmList<Section>());
        form.getSections().addAll(sections);
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

    public void updateQuestionStatus(Integer questionId) {
        realm = Realm.getDefaultInstance();
        Question question = realm
                .where(Question.class)
                .equalTo("idIntrebare", questionId)
                .findFirst();

        realm.beginTransaction();
        question.setSynced(true);
        realm.copyToRealmOrUpdate(question);
        realm.commitTransaction();

        realm.close();
    }
}
