package ro.code4.votehack.net.model;

import java.io.Serializable;

public class Version implements Serializable {
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
