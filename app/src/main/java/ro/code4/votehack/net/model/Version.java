package ro.code4.votehack.net.model;

import java.io.Serializable;

import io.realm.RealmObject;

public class Version extends RealmObject implements Serializable {
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
