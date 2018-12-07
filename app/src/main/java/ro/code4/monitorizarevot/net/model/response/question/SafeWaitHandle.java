package ro.code4.monitorizarevot.net.model.response.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SafeWaitHandle {

    @SerializedName("isInvalid")
    @Expose
    private Boolean isInvalid;

    @SerializedName("isClosed")
    @Expose
    private Boolean isClosed;

    public Boolean getInvalid() {
        return isInvalid;
    }

    public void setInvalid(Boolean invalid) {
        isInvalid = invalid;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }
}
