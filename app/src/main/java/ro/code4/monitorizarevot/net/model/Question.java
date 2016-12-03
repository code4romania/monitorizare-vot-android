package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class Question extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    private Integer idIntrebare;
    @Expose
    private String textIntrebare;
    @Expose
    private String codIntrebare;
    @Expose
    private Integer idTipIntrebare;
    @Expose
    private RealmList<Answer> raspunsuriDisponibile;
    private Branch branch;
    private boolean isSynced;

    public Integer getId() {
        return idIntrebare;
    }

    public String getText() {
        return textIntrebare;
    }

    public String getCode() {
        return codIntrebare;
    }

    public Integer getTypeId() {
        return idTipIntrebare;
    }

    public List<Answer> getAnswerList() {
        return raspunsuriDisponibile;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<ResponseAnswer> getRaspunsuriIntrebare() {
        if(branch != null){
            return branch.getRaspunsuriIntrebare();
        }
        return new ArrayList<>();
    }
}
