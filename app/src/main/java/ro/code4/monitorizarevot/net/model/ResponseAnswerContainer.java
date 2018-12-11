package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseAnswerContainer implements Serializable {

    // TODO serialized names to be translated when api is updated
    @SerializedName("raspuns")
    @Expose
    private List<QuestionAnswer> responseMapperList;

    public ResponseAnswerContainer(List<QuestionAnswer> responseMapperList){
        this.responseMapperList = responseMapperList;
    }

    public List<QuestionAnswer> getResponseMapperList() {
        return responseMapperList;
    }

    public void setResponseMapperList(List<QuestionAnswer> responseMapperList) {
        this.responseMapperList = responseMapperList;
    }
}
