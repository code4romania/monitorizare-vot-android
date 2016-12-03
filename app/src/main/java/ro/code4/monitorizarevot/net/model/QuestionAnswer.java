package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class QuestionAnswer implements Serializable {
    @Expose
    private Integer idIntrebare;
    @Expose
    private String codJudet;
    @Expose
    private Integer numarSectie;
    @Expose
    private String codFormular;
    @Expose
    private List<ResponseAnswer> optiuni;

    public QuestionAnswer(CityBranch cityBranch, String formId) {
        this.idIntrebare = cityBranch.getQuestionId();
        this.codJudet = cityBranch.getCountryCode();
        this.numarSectie = cityBranch.getBranch();
        this.codFormular = formId;
        this.optiuni = cityBranch.getRaspunsuriIntrebare();
    }

    public Integer getIdIntrebare() {
        return idIntrebare;
    }

    public void setIdIntrebare(Integer idIntrebare) {
        this.idIntrebare = idIntrebare;
    }

    public String getCodJudet() {
        return codJudet;
    }

    public void setCodJudet(String codJudet) {
        this.codJudet = codJudet;
    }

    public Integer getNumarSectie() {
        return numarSectie;
    }

    public void setNumarSectie(Integer numarSectie) {
        this.numarSectie = numarSectie;
    }

    public String getCodFormular() {
        return codFormular;
    }

    public void setCodFormular(String codFormular) {
        this.codFormular = codFormular;
    }

    public List<ResponseAnswer> getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(List<ResponseAnswer> optiuni) {
        this.optiuni = optiuni;
    }
}
