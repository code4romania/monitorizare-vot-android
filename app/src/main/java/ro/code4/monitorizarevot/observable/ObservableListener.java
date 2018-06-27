package ro.code4.monitorizarevot.observable;

import rx.Observer;

public abstract class ObservableListener<T> implements Observer<T> {

    @Override
    public final void onCompleted() {
        onSuccess();
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {
    }

    public abstract void onSuccess();

}