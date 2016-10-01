package ro.code4.votehack.net.model;

import java.io.Serializable;

public class Answer implements Serializable {
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
