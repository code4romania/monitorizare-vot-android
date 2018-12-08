package ro.code4.monitorizarevot.data.chat.socket;

import rx.Observable;

public interface SocketConnector {

    String CHAT_HISTORY = "chat_history";

    String NEW_MESSAGE = "new_message";

    Observable<SocketEvent> history();

    Observable<SocketEvent> newMessage();

    Observable<Boolean> sendMessage(String message);

    void disconnect();
}
