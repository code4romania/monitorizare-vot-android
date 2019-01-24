package ro.code4.monitorizarevot.domain.exception;

public class VoteException extends Exception {

    private MessageType mType;

    public VoteException(MessageType type) {
        super();
        mType = type;
    }

    public MessageType getType() {
        return mType;
    }
}
