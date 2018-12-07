package ro.code4.monitorizarevot.presentation;

public class LoadingMessage {

    private boolean mIsLoading;

    private String mMessage;

    public LoadingMessage() {
        this(true);
    }

    public LoadingMessage(String message) {
        this(true, message);
    }

    public LoadingMessage(boolean isLoading) {
        this(isLoading, null);
    }

    public LoadingMessage(boolean isLoading, String message) {
        mIsLoading = isLoading;
        mMessage = message;
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public String getMessage() {
        return mMessage;
    }
}
