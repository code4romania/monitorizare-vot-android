package ro.code4.monitorizarevot.net.model.response;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ro.code4.monitorizarevot.db.Preferences;

public class ResponseAnswer extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    private Integer idOptiune;
    @Expose
    private String value;
    private String codJudet;
    private Integer numarSectie;

    public ResponseAnswer(){
        this.codJudet = Preferences.getCountyCode();
        this.numarSectie = Preferences.getBranchNumber();
    }

    public ResponseAnswer(Integer idOptiune) {
        this();
        this.idOptiune = idOptiune;
    }

    public ResponseAnswer(Integer idOptiune, String textRaspuns) {
        this();
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
        this.value = value;
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
}
