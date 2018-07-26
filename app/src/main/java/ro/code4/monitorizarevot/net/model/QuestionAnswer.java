package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class QuestionAnswer implements Serializable {

    // TODO serialized names to be translated when api is updated
    @Expose
    @SerializedName("idIntrebare")
    private Integer questionId;

    @Expose
    @SerializedName("codJudet")
    private String countyCode;

    @Expose
    @SerializedName("numarSectie")
    private Integer sectionNumber;

    @Expose
    @SerializedName("codFormular")
    private String formCode;

    @Expose
    @SerializedName("optiuni")
    private List<ResponseAnswer> options;

    public QuestionAnswer(BranchQuestionAnswer branchQuestionAnswer, String formId) {
        this.questionId = branchQuestionAnswer.getQuestionId();
        this.countyCode = branchQuestionAnswer.getCountryCode();
        this.sectionNumber = branchQuestionAnswer.getBranchNumber();
        this.formCode = formId;
        this.options = branchQuestionAnswer.getAnswers();
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public Integer getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(Integer sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public List<ResponseAnswer> getOptions() {
        return options;
    }

    public void setOptions(List<ResponseAnswer> options) {
        this.options = options;
    }
}
