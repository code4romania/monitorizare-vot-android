package ro.code4.monitorizarevot.presentation.rx;

import ro.code4.monitorizarevot.presentation.Message;
import ro.code4.monitorizarevot.presentation.MessageFactory;
import rx.Observer;

public abstract class DefaultObserver<T> implements Observer<T> {

    private final MessageFactory mMessageFactory;

    public DefaultObserver(MessageFactory messageFactory) {
        super();
        mMessageFactory = messageFactory;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable throwable) {
        onErrorMessage(mMessageFactory.getErrorMessage(throwable));
    }

    @Override
    public void onNext(T t) {
    }

    public void onErrorMessage(Message message) {
    }
}
