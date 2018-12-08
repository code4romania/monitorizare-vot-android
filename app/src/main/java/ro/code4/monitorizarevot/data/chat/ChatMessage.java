package ro.code4.monitorizarevot.data.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ChatMessage extends RealmObject {

    @PrimaryKey
    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("timestamp")
    private long mTimestamp;

    @Expose
    @SerializedName("type")
    private int mType;

    public ChatMessage() {
        this(null, 0, -1);
    }

    public ChatMessage(String message, long timestamp, int type) {
        mMessage = message;
        mTimestamp = timestamp;
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
