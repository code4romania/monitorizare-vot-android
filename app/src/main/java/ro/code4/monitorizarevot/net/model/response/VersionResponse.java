package ro.code4.monitorizarevot.net.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ro.code4.monitorizarevot.net.model.FormDetails;

public class VersionResponse implements Serializable {

    // TODO serialized names to be translated when api is updated
    @Expose
    @SerializedName("formulare")
    private List<FormDetails> formDetailsList;

    public List<FormDetails> getFormDetailsList() {
        return formDetailsList;
    }
}