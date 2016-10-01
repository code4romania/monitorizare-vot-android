package ro.code4.votehack.net.model;

import java.io.Serializable;
import java.util.List;

public class Section implements Serializable {
    private String codSectiune;
    private String descriere;
    private List<Question> intrebari;

    public String getSectionCode() {
        return codSectiune;
    }

    public String getDescription() {
        return descriere;
    }

    public List<Question> getQuestionList() {
        return intrebari;
    }
}