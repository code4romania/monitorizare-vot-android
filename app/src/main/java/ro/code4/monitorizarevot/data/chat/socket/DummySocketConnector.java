package ro.code4.monitorizarevot.data.chat.socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

@Singleton
public class DummySocketConnector implements SocketConnector {

    private String echoMessage = "{\"message\": \"%s\" , \"type\": 1}";

    private List<String> mMessages = new ArrayList<>();

    private BehaviorSubject<SocketEvent> newMessageSubject;

    @Inject
    public DummySocketConnector() {
    }

    @Override
    public Observable<SocketEvent> history() {
        Object[] args = mMessages.toArray();

        final SocketEvent socketEvent = new SocketEvent(args, CHAT_HISTORY);

        return Observable.create(new Observable.OnSubscribe<SocketEvent>() {

            @Override
            public void call(Subscriber<? super SocketEvent> subscriber) {
                subscriber.onNext(socketEvent);
            }
        });
    }

    @Override
    public Observable<SocketEvent> newMessage() {
        if (newMessageSubject == null || newMessageSubject.hasThrowable()) {
            newMessageSubject = BehaviorSubject.create();
        }

        return newMessageSubject;
    }

    @Override
    public Observable<Boolean> sendMessage(final String message) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                mMessages.add(message);

                subscriber.onNext(true);

                Object[] args = new Object[1];
                args[0] = message;

                newMessageSubject.onNext(new SocketEvent(args, NEW_MESSAGE));
            }
        }).delay(1, TimeUnit.SECONDS).map(new Func1<Boolean, Boolean>() {

            @Override
            public Boolean call(Boolean aBoolean) {
                Object[] argsEcho = new Object[1];

                try {
                    JSONObject jsonObject = new JSONObject(message);

                    argsEcho[0] = String.format(echoMessage, "Echo Message: " + jsonObject.optString("message"));

                    mMessages.add((String) argsEcho[0]);

                    newMessageSubject.onNext(new SocketEvent(argsEcho, NEW_MESSAGE));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return aBoolean;
            }
        });
    }

    @Override
    public void disconnect() {
        mMessages.clear();
        newMessageSubject = null;
    }
}
