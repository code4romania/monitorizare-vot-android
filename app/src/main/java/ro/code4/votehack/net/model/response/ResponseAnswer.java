package ro.code4.votehack.net.model.response;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ResponseAnswer extends RealmObject implements Serializable{
    @PrimaryKey
    private Integer idOptiune;
    private String value;
    private boolean isSynced;

    public ResponseAnswer(){

    }

    public ResponseAnswer(Integer idOptiune) {
        this.idOptiune = idOptiune;
    }

    public ResponseAnswer(Integer idOptiune, String textRaspuns) {
        this.idOptiune = idOptiune;
        this.value = textRaspuns;
    }

    public Integer getIdOptiune() {
        return idOptiune;
    }

    public void setIdOptiune(Integer idOptiune) {
        this.idOptiune = idOptiune;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
