package ro.code4.votehack.net.model;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Question extends RealmObject implements Serializable {
    @PrimaryKey
    private Integer idIntrebare;
    private String textIntrebare;
    private Integer idTipIntrebare;
    private RealmList<Answer> raspunsuriDisponibile;

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
