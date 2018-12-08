package ro.code4.monitorizarevot.data.repository;

import com.google.gson.Gson;

import java.util.ArrayList;
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
                return mapHistory(socketEvent.getArguments());
            }
        });
    }

    @Override
    public Observable<Boolean> sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(System.currentTimeMillis());
        chatMessage.setType(ChatMessageType.SENT.ordinal());

        return mSocketConnector.sendMessage(new Gson().toJson(chatMessage, ChatMessage.class));
    }

    @Override
    public void closeChat() {
        mSocketConnector.disconnect();
    }

    private ChatMessage mapMessage(Object data) {
        return new Gson().fromJson(data.toString(), ChatMessage.class);
    }

    private List<ChatMessage> mapHistory(Object[] args) {
        List<ChatMessage> list = new ArrayList<>();

        if (args != null) {
            for (Object data : args) {
                ChatMessage message = mapMessage(data);
                list.add(message);
            }
        }

        return list;
    }
}
