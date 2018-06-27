package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseAnswerContainer implements Serializable {

    @SerializedName("raspuns")
    @Expose
    private List<QuestionAnswer> responseMapperList;

    public ResponseAnswerContainer(List<QuestionAnswer> responseMapperList){
        this.responseMapperList = responseMapperList;
    }

    public List<QuestionAnswer> getReponseMapperList() {
        return responseMapperList;
    }

    public void setReponseMapperList(List<QuestionAnswer> reponseMapperList) {
        this.responseMapperList = reponseMapperList;
    }
}
