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

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public AsyncWaitHandle getAsyncWaitHandle() {
        return asyncWaitHandle;
    }

    public void setAsyncWaitHandle(AsyncWaitHandle asyncWaitHandle) {
        this.asyncWaitHandle = asyncWaitHandle;
    }

    public AsyncState getAsyncState() {
        return asyncState;
    }

    public void setAsyncState(AsyncState asyncState) {
        this.asyncState = asyncState;
    }

    public Boolean getCompletedSynchronously() {
        return completedSynchronously;
    }

    public void setCompletedSynchronously(Boolean completedSynchronously) {
        this.completedSynchronously = completedSynchronously;
    }
}
