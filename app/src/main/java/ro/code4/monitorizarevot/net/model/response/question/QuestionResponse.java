
package ro.code4.monitorizarevot.net.model.response.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionResponse {

    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;
    @SerializedName("asyncWaitHandle")
    @Expose
    private AsyncWaitHandle asyncWaitHandle;
    @SerializedName("asyncState")
    @Expose
    private AsyncState asyncState;
    @SerializedName("completedSynchronously")
    @Expose
    private Boolean completedSynchronously;

    /**
     * 
     * @return
     *     The isCompleted
     */
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * 
     * @param isCompleted
     *     The isCompleted
     */
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * 
     * @return
     *     The asyncWaitHandle
     */
    public AsyncWaitHandle getAsyncWaitHandle() {
        return asyncWaitHandle;
    }

    /**
     * 
     * @param asyncWaitHandle
     *     The asyncWaitHandle
     */
    public void setAsyncWaitHandle(AsyncWaitHandle asyncWaitHandle) {
        this.asyncWaitHandle = asyncWaitHandle;
    }

    /**
     * 
     * @return
     *     The asyncState
     */
    public AsyncState getAsyncState() {
        return asyncState;
    }

    /**
     * 
     * @param asyncState
     *     The asyncState
     */
    public void setAsyncState(AsyncState asyncState) {
        this.asyncState = asyncState;
    }

    /**
     * 
     * @return
     *     The completedSynchronously
     */
    public Boolean getCompletedSynchronously() {
        return completedSynchronously;
    }

    /**
     * 
     * @param completedSynchronously
     *     The completedSynchronously
     */
    public void setCompletedSynchronously(Boolean completedSynchronously) {
        this.completedSynchronously = completedSynchronously;
    }

}
