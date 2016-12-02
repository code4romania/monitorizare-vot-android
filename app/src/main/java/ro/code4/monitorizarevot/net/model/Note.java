package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {
    @PrimaryKey
    private int id;
    @Expose
    private String uriPath;
    @Expose
    private String description;
    @Expose
    private String codJudet;
    @Expose
    private Integer numarSectie;
    @Expose
    private Integer idIntrebare;

    public int getId() {
        return id;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountyCode() {
        return codJudet;
    }

    public void setCountyCode(String countyCode) {
        this.codJudet = countyCode;
    }

    public Integer getBranchNumber() {
        return numarSectie;
    }

    public void setBranchNumber(Integer branchNumber) {
        this.numarSectie = branchNumber;
    }

    public Integer getQuestionId() {
        return idIntrebare;
    }

    public void setQuestionId(Integer questionId) {
        this.idIntrebare = questionId;
    }
}
