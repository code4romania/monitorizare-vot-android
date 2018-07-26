package ro.code4.monitorizarevot.net.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class BranchQuestionAnswer extends RealmObject {

    @PrimaryKey
    private String id;

    private String countryCode;
    private int branchNumber;
    private int questionId;
    private BranchDetails branchDetails;
    private RealmList<ResponseAnswer> answers = new RealmList<>();

    public BranchQuestionAnswer(){

    }

    public BranchQuestionAnswer(String countryCode, int branchNumber) {
        this.countryCode = countryCode;
        this.branchNumber = branchNumber;
    }

    public BranchQuestionAnswer(Integer questionId){
        this(Preferences.getCountyCode(), Preferences.getBranchNumber());
        this.questionId = questionId;
    }

    public BranchQuestionAnswer(Integer questionId, List<ResponseAnswer> answers) {
        this(questionId);
        this.answers.clear();
        this.answers.addAll(answers);
        this.id = countryCode + String.valueOf(branchNumber) + String.valueOf(questionId);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public List<ResponseAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ResponseAnswer> answers) {
        this.answers.clear();
        this.answers.addAll(answers);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    public BranchDetails getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(BranchDetails branchDetails) {
        this.branchDetails = branchDetails;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object obj) {
        BranchQuestionAnswer branchQuestionAnswer = (BranchQuestionAnswer) obj;
        return countryCode.equalsIgnoreCase(branchQuestionAnswer.getCountryCode())
                && this.branchNumber == branchQuestionAnswer.getBranchNumber()
                && questionId == branchQuestionAnswer.questionId;
    }
}
