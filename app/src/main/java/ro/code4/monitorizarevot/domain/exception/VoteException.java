package ro.code4.monitorizarevot.domain.exception;

public class VoteException extends Exception {

    private MessageType mType;
    private String mCustomMessage;

    public VoteException(MessageType type) {
        super();
        mType = type;
    }

    public VoteException(MessageType type, String customMessage) {
        this(type);
        mCustomMessage = customMessage;
    }

    public MessageType getType() {
        return mType;
    }

    public String getCustomMessage() {
        return mCustomMessage;
    }

    public boolean hasCustomMessage() {
        return mCustomMessage != null && !mCustomMessage.trim().isEmpty();
    }
}
