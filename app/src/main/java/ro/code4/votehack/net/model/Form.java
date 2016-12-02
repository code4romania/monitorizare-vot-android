package ro.code4.votehack.net.model;

import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Form extends RealmObject {
    @PrimaryKey
    @Expose
    private String id;
    @Expose
    private RealmList<Section> sectiuni;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Section> getSections() {
        return sectiuni;
    }

    public void setSections(RealmList<Section> sectiuni) {
        this.sectiuni = sectiuni;
    }
}
