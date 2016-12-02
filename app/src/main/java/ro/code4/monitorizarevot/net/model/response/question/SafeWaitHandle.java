
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

    /**
     * 
     * @return
     *     The isInvalid
     */
    public Boolean getIsInvalid() {
        return isInvalid;
    }

    /**
     * 
     * @param isInvalid
     *     The isInvalid
     */
    public void setIsInvalid(Boolean isInvalid) {
        this.isInvalid = isInvalid;
    }

    /**
     * 
     * @return
     *     The isClosed
     */
    public Boolean getIsClosed() {
        return isClosed;
    }

    /**
     * 
     * @param isClosed
     *     The isClosed
     */
    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

}
