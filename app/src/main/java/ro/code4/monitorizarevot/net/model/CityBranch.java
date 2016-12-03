package ro.code4.monitorizarevot.net.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class CityBranch extends RealmObject{
    @PrimaryKey
    private String cityBranchId;
    private String countryCode;
    private int branch;
    private int questionId;
    private RealmList<ResponseAnswer> raspunsuriIntrebare = new RealmList<>();

    public CityBranch(){

    }

    public CityBranch(String codJudet, int numarSectie) {
        this.countryCode = codJudet;
        this.branch = numarSectie;
    }

    public CityBranch(Integer questionId){
        this(Preferences.getCountyCode(), Preferences.getBranchNumber());
        this.questionId = questionId;
    }

    public CityBranch(Integer questionId, List<ResponseAnswer> answers) {
        this(questionId);
        this.raspunsuriIntrebare.clear();
        this.raspunsuriIntrebare.addAll(answers);
        this.cityBranchId = countryCode + String.valueOf(branch) + String.valueOf(questionId);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getBranch() {
        return branch;
    }

    public List<ResponseAnswer> getRaspunsuriIntrebare() {
        return raspunsuriIntrebare;
    }

    public void setRaspunsuriIntrebare(List<ResponseAnswer> raspunsuriIntrebare) {
        this.raspunsuriIntrebare.clear();
        this.raspunsuriIntrebare.addAll(raspunsuriIntrebare);
    }

    public String getCityBranchId() {
        return cityBranchId;
    }

    public void setCityBranchId(String cityBranchId) {
        this.cityBranchId = cityBranchId;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object obj) {
        CityBranch cityBranch = (CityBranch) obj;
        return countryCode.equalsIgnoreCase(cityBranch.getCountryCode())
                && branch == cityBranch.getBranch()
                && questionId == cityBranch.questionId;
    }
}
