package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Answer extends RealmObject implements Serializable {

    // TODO serialized names to be translated when api is updated
    @PrimaryKey
    @Expose
    @SerializedName("idOptiune")
    private Integer id;

    @Expose
    @SerializedName("textOptiune")
    private String text;

    @Expose
    @SerializedName("seIntroduceText")
    private boolean hasManualInput;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean hasManualInput() {
        return hasManualInput;
    }
}
