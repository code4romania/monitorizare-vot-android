package ro.code4.votehack.db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ro.code4.votehack.net.model.Form;
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

    public Form getForm(String formCode) {
        realm = Realm.getDefaultInstance();
        RealmResults<Form> results = realm
                .where(Form.class)
                .equalTo("codSectiune", formCode)
                .findAll();
        Form result = results.size() > 0 ? results.get(0) : null;
        realm.close();
        return result;
    }

    public Version getFormVersion() {
        RealmResults<Version> queryResult = Realm.getDefaultInstance()
                .where(Version.class)
                .findAll();
        return queryResult.size() > 0 ? queryResult.first() : null;
    }

    public Question getQuestion(Integer questionId) {
        realm = Realm.getDefaultInstance();
        RealmResults<Question> results = realm
                .where(Question.class)
                .equalTo("idIntrebare", questionId)
                .findAll();
        Question result = results.size() > 0 ? results.get(0) : null;
        realm.close();
        return result;
    }

    public void saveAnswerResponse(Question question, List<ResponseAnswer> responseAnswer) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        question.setRaspunsuriIntrebare(responseAnswer);
        realm.copyToRealmOrUpdate(question);
        realm.commitTransaction();
        realm.close();
    }

    public void saveFormsDefinition(List<Form> forms) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(forms);
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
}
