package ro.code4.monitorizarevot.net.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ro.code4.monitorizarevot.db.Preferences;

public class ResponseAnswer extends RealmObject implements Serializable {

    //TODO update fields to English names - to check names if influenced by backend

    @PrimaryKey
    @Expose
    @SerializedName("idOptiune")
    private Integer optionId;

    @Expose
    @SerializedName("value")
    private String value;

    @SerializedName("codJudet")
    private String countyCode;

    @SerializedName("numarSectie")
    private int branchNumber;

    public ResponseAnswer() {
        this.countyCode = Preferences.getCountyCode();
        this.branchNumber = Preferences.getBranchNumber();
    }

    public ResponseAnswer(Integer optionId) {
        this();
        this.optionId = optionId;
    }

    public ResponseAnswer(Integer idOptiune, String textRaspuns) {
        this(idOptiune);
        this.value = textRaspuns;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseAnswer that = (ResponseAnswer) o;

        return branchNumber == that.branchNumber &&
                optionId.intValue() == that.optionId &&
                (value == null ? that.value == null : value.equals(that.value)) &&
                (countyCode == null ? that.countyCode == null : countyCode.equals(that.countyCode));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + branchNumber;
        result = prime * result + optionId;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + ((countyCode == null) ? 0 : countyCode.hashCode());
        return result;
    }
}
