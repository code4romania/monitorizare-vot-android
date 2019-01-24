package ro.code4.monitorizarevot.presentation;

import android.content.Context;

import javax.inject.Inject;

import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.domain.exception.MessageType;
import ro.code4.monitorizarevot.domain.exception.VoteException;

import static ro.code4.monitorizarevot.domain.exception.MessageType.UNKNOWN;

public class MessageFactory {

    private final Context mContext;

    @Inject
    public MessageFactory(Context context) {
        mContext = context;
    }

    public Message getMessage(MessageType type) {
        String message;

        switch (type) {
            case EMPTY_CREDENTIALS:
                message = mContext.getString(R.string.empty_credential_message);
                break;

            case SERVER_ERROR:
                message = "Eroare de server";
                break;

            default:
                message = "unknown error";
                break;
        }

        return new Message(type, message);
    }

    public Message getErrorMessage(Throwable throwable) {
        if (throwable == null || !(throwable instanceof VoteException)) {
            return new Message(UNKNOWN, "unknown error");
        }

        VoteException exc = (VoteException) throwable;

        return getMessage(exc.getType());
    }
}
