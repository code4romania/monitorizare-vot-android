package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Answer extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    private Integer idOptiune;
    @Expose
    private String textOptiune;
    @Expose
    private boolean seIntroduceText;

    public Integer getId() {
        return idOptiune;
    }

    public String getText() {
        return textOptiune;
    }

    public boolean hasManualInput() {
        return seIntroduceText;
    }
}
