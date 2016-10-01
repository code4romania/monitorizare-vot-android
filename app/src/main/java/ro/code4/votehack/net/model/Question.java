package ro.code4.votehack.net.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private Integer idIntrebare;
    private String textIntrebare;
    private Integer idTipIntrebare;
    private List<Answer> raspunsuriDisponibile;

    public Integer getId() {
        return idIntrebare;
    }

    public String getText() {
        return textIntrebare;
    }

    public Integer getTypeId() {
        return idTipIntrebare;
    }

    public List<Answer> getAnswerList() {
        return raspunsuriDisponibile;
    }
}
