package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Version extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    private int id = 1;

    @Expose
    private Integer A, B, C;

    public Integer getA() {
        return A;
    }

    public Integer getB() {
        return B;
    }

    public Integer getC() {
        return C;
    }
}
