package ro.code4.votehack.net.model;


import java.io.Serializable;
import java.util.List;

import ro.code4.votehack.net.model.response.ResponseAnswer;

public class QuestionAnswer implements Serializable {

    private Integer idIntrebare;
    private String codJudet;
    private Integer numarSectie;
    private String codFormular;
    private List<ResponseAnswer> optiuni;

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
