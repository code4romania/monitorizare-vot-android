package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Form extends RealmObject {

    // TODO serialized names to be translated when api is updated
    @PrimaryKey
    @Expose
    private String id;

    @Expose
    @SerializedName("sectiuni")
    private RealmList<Section> sections;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(RealmList<Section> sections) {
        this.sections = sections;
    }
}
