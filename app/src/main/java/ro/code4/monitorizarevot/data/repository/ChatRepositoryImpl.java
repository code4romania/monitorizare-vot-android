package ro.code4.monitorizarevot.data.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.data.chat.ChatMessage;
import ro.code4.monitorizarevot.data.chat.ChatMessageType;
import ro.code4.monitorizarevot.data.chat.socket.SocketConnector;
import ro.code4.monitorizarevot.data.chat.socket.SocketEvent;
import ro.code4.monitorizarevot.domain.repository.ChatRepository;
import rx.Observable;
import rx.functions.Func1;

@Singleton
public class ChatRepositoryImpl implements ChatRepository {

    private final SocketConnector mSocketConnector;

    @Inject
    public ChatRepositoryImpl(SocketConnector socketConnector) {
        mSocketConnector = socketConnector;
    }

    @Override
    public Observable<ChatMessage> newMessage() {
        return mSocketConnector.newMessage().map(new Func1<SocketEvent, ChatMessage>() {

            @Override
            public ChatMessage call(SocketEvent socketEvent) {
                return mapMessage(socketEvent.getArguments()[0]);
            }
        });
    }

    @Override
    public Observable<List<ChatMessage>> history() {
        return mSocketConnector.history().map(new Func1<SocketEvent, List<ChatMessage>>() {

            @Override
            public List<ChatMessage> call(SocketEvent socketEvent) {
                return mapHistory(socketEvent.getArguments()[0]);
            }
        });
    }

    @Override
    public Observable<Boolean> sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(System.currentTimeMillis());
        chatMessage.setType(ChatMessageType.SENT.ordinal());

        mSocketConnector.sendMessage(new Gson().toJson(chatMessage, ChatMessage.class));

        return Observable.just(true);
    }

    @Override
    public void closeChat() {
        mSocketConnector.disconnect();
    }

    private ChatMessage mapMessage(Object data) {
        ChatMessage chatMessage = new Gson().fromJson(data.toString(), ChatMessage.class);
        chatMessage.setType(ChatMessageType.RECEIVED.ordinal());

        return new Gson().fromJson(data.toString(), ChatMessage.class);
    }

    private List<ChatMessage> mapHistory(Object data) {
        Type listType = new TypeToken<List<ChatMessage>>() {
        }.getType();

        return new Gson().fromJson(data.toString(), listType);
    }
}
