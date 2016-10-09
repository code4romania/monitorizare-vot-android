package ro.code4.votehack.net.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Answer extends RealmObject implements Serializable {
    @PrimaryKey
    private Integer idOptiune;
    private String textOptiune;
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
