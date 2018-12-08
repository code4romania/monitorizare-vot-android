package ro.code4.monitorizarevot.domain.repository;

import java.util.List;

import ro.code4.monitorizarevot.data.chat.ChatMessage;
import rx.Observable;

public interface ChatRepository extends DataRepository {

    Observable<ChatMessage> newMessage();

    Observable<List<ChatMessage>> history();

    Observable<Boolean> sendMessage(String message);

    void closeChat();
}
