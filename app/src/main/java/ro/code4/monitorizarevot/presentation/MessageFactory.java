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

    private Message getMessage(VoteException exc) {
        return exc.hasCustomMessage()
                ? buildMessage(exc.getType(), exc.getCustomMessage())
                : getMessage(exc.getType());
    }

    private Message getMessage(MessageType type) {
        String message;

        switch (type) {
            case EMPTY_CREDENTIALS:
                message = mContext.getString(R.string.error_empty_credentials);
                break;

            case INVALID_PHONE_NUMBER:
                message = mContext.getString(R.string.error_invalid_phone_number);
                break;

            case NO_INTERNET_CONNECTION:
                message = mContext.getString(R.string.error_no_connection);
                break;

            case SERVER_ERROR:
                message = mContext.getString(R.string.error_server);
                break;

            default:
                message = mContext.getString(R.string.error_unknown);
                break;
        }

        return buildMessage(type, message);
    }

    public Message getErrorMessage(Throwable throwable) {
        if (throwable instanceof VoteException) {
            return getMessage((VoteException) throwable);
        } else {
            return getMessage(UNKNOWN);
        }
    }

    private Message buildMessage(MessageType type, String message) {
        return new Message(type, message);
    }
}
