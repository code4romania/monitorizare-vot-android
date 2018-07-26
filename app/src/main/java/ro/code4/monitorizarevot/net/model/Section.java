package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Section extends RealmObject implements Serializable {

    // TODO serialized names to be translated when api is updated
    @PrimaryKey
    @Expose
    @SerializedName("codSectiune")
    private String code;

    @Expose
    @SerializedName("descriere")
    private String description;

    @Expose
    @SerializedName("intrebari")
    private RealmList<Question> questions;

    public String getSectionCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestionList() {
        return questions;
    }
}