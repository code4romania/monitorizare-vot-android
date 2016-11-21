package ro.code4.votehack.db;

import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmResults;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.net.model.Section;
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

    public Data() {

    }

    public Section getSectionA() {
        return getSection("A");
    }

    public Section getSectionB() {
        return getSection("B");
    }

    public Section getSectionC() {
        return getSection("C");
    }

    public Section getSection(String sectionCode) {
        realm = Realm.getDefaultInstance();
        RealmResults<Section> results = realm
                .where(Section.class)
                .equalTo("codSectiune", sectionCode)
                .findAll();
        Section result = results.size() > 0 ? results.get(0) : null;
        realm.close();
        return result;
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

    public void saveAnswerResponse(Question question, ResponseAnswer responseAnswer) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ResponseAnswer answer = realm.createOrUpdateObjectFromJson(ResponseAnswer.class, new Gson().toJson(responseAnswer));
        question.setRaspunsIntrebare(answer);
        realm.copyToRealmOrUpdate(question);
        realm.commitTransaction();
        realm.close();
    }
}
