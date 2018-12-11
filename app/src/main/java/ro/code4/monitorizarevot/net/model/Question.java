package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class Question extends RealmObject implements Serializable {

    // TODO serialized names to be translated when api is updated
    @PrimaryKey
    @Expose
    @SerializedName("idIntrebare")
    private Integer id;

    @Expose
    @SerializedName("textIntrebare")
    private String text;

    @Expose
    @SerializedName("codIntrebare")
    private String code;

    @Expose
    @SerializedName("idTipIntrebare")
    private Integer typeId;

    @Expose
    @SerializedName("raspunsuriDisponibile")
    private RealmList<Answer> answerList;

    private BranchQuestionAnswer branchQuestionAnswer;
    private boolean isSynced;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public BranchQuestionAnswer getBranchQuestionAnswer() {
        return branchQuestionAnswer;
    }

    public void setBranchQuestionAnswer(BranchQuestionAnswer branchQuestionAnswer) {
        this.branchQuestionAnswer = branchQuestionAnswer;
    }

    public List<ResponseAnswer> getAnswers() {
        if(branchQuestionAnswer != null){
            return branchQuestionAnswer.getAnswers();
        }
        return new ArrayList<>();
    }
}
