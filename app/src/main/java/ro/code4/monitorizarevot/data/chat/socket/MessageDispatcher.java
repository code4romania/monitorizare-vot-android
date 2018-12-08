package ro.code4.monitorizarevot.data.chat.socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.data.chat.ChatMessage;
import ro.code4.monitorizarevot.data.chat.ChatMessageType;
import rx.Observable;
import rx.functions.Func1;

@Singleton
public class MessageDispatcher {

    private final SocketConnector mSocketConnector;

    @Inject
    public MessageDispatcher(SocketConnector socketConnector) {
        mSocketConnector = socketConnector;
    }

    public Observable<ChatMessage> newMessage() {
        return mSocketConnector.newMessage().map(new Func1<SocketEvent, ChatMessage>() {

            @Override
            public ChatMessage call(SocketEvent socketEvent) {
                return mapMessage(socketEvent.getArguments()[0]);
            }
        });
    }

    public Observable<List<ChatMessage>> history() {
        return mSocketConnector.history().map(new Func1<SocketEvent, List<ChatMessage>>() {

            @Override
            public List<ChatMessage> call(SocketEvent socketEvent) {
                return mapHistory(socketEvent.getArguments()[0]);
            }
        });
    }

    public void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(System.currentTimeMillis());
        chatMessage.setType(ChatMessageType.SENT.ordinal());

        mSocketConnector.sendMessage(new Gson().toJson(chatMessage, ChatMessage.class));
    }

    public void close() {
        mSocketConnector.disconnect();
    }

    private ChatMessage mapMessage(Object data) {
        return new Gson().fromJson(data.toString(), ChatMessage.class);
    }

    private List<ChatMessage> mapHistory(Object data) {
        Type listType = new TypeToken<List<ChatMessage>>() {
        }.getType();

        return new Gson().fromJson(data.toString(), listType);
    }
}
