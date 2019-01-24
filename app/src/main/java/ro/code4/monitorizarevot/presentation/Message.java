package ro.code4.monitorizarevot.presentation;

import ro.code4.monitorizarevot.domain.exception.MessageType;

public class Message {

    private MessageType mType;

    private String mMessage;

    public Message(MessageType type, String message) {
        mType = type;
        mMessage = message;
    }

    public MessageType getType() {
        return mType;
    }

    public String getMessage() {
        return mMessage;
    }
}
