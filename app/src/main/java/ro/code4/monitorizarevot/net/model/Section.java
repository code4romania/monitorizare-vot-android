package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Section extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    private String codSectiune;
    @Expose
    private String descriere;
    @Expose
    private RealmList<Question> intrebari;

    public String getSectionCode() {
        return codSectiune;
    }

    public String getDescription() {
        return descriere;
    }

    public List<Question> getQuestionList() {
        return intrebari;
    }
}