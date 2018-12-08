package ro.code4.monitorizarevot.data.chat.socket;

public class SocketEvent {

    private Object[] mArguments;

    private String mEvent;

    public SocketEvent(Object[] arguments, String event) {
        mArguments = arguments;
        mEvent = event;
    }

    public Object[] getArguments() {
        return mArguments;
    }

    public String getEvent() {
        return mEvent;
    }
}
