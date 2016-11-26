package ro.code4.votehack.net.model;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ro.code4.votehack.net.model.response.ResponseAnswer;

public class Question extends RealmObject implements Serializable {
    @PrimaryKey
    private Integer idIntrebare;
    private String textIntrebare;
    private Integer idTipIntrebare;
    private RealmList<Answer> raspunsuriDisponibile;
    private RealmList<ResponseAnswer> raspunsuriIntrebare = new RealmList<>();

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

    public List<ResponseAnswer> getRaspunsuriIntrebare() {
        return raspunsuriIntrebare;
    }

    public void setRaspunsuriIntrebare(List<ResponseAnswer> raspunsuriIntrebare) {
        this.raspunsuriIntrebare.clear();
        this.raspunsuriIntrebare.addAll(raspunsuriIntrebare);
    }
}
