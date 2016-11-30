package ro.code4.votehack.net.model.response;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ResponseAnswer extends RealmObject implements Serializable{
    @PrimaryKey
    @Expose
    private Integer idOptiune;
    @Expose
    private String value;
    private String codJudet;
    private Integer numarSectie;

    public ResponseAnswer(){

    }

    public ResponseAnswer(Integer idOptiune) {
        this.idOptiune = idOptiune;
    }

    public ResponseAnswer(Integer idOptiune, String textRaspuns) {
        this.idOptiune = idOptiune;
        this.value = textRaspuns;
    }

    public Integer getIdOptiune() {
        return idOptiune;
    }

    public void setIdOptiune(Integer idOptiune) {
        this.idOptiune = idOptiune;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;}

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
}
