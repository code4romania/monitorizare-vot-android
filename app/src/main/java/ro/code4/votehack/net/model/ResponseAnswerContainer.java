package ro.code4.votehack.net.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseAnswerContainer implements Serializable {
    @SerializedName("raspuns")
    private List<QuestionAnswer> responseMapperList;

    public List<QuestionAnswer> getReponseMapperList() {
        return responseMapperList;
    }

    public void setReponseMapperList(List<QuestionAnswer> reponseMapperList) {
        this.responseMapperList = reponseMapperList;
    }
}
