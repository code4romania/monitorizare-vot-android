
package ro.code4.monitorizarevot.net.model.response.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AsyncWaitHandle {

    @SerializedName("safeWaitHandle")
    @Expose
    private SafeWaitHandle safeWaitHandle;

    /**
     * 
     * @return
     *     The safeWaitHandle
     */
    public SafeWaitHandle getSafeWaitHandle() {
        return safeWaitHandle;
    }

    /**
     * 
     * @param safeWaitHandle
     *     The safeWaitHandle
     */
    public void setSafeWaitHandle(SafeWaitHandle safeWaitHandle) {
        this.safeWaitHandle = safeWaitHandle;
    }

}
