package ro.code4.monitorizarevot.observable;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ObservableRequest<T> {
    private Observable<T> mObservable;
    private Scheduler mWorkerThread = Schedulers.io();
    private Scheduler mUiThread = AndroidSchedulers.mainThread();

    public static abstract class OnRequested<T> {
        public abstract void onRequest(Subscriber<? super T> repoListener);
    }

    public ObservableRequest(final OnRequested<T> onRequested) {
        this(false, onRequested);
    }

    public ObservableRequest(boolean workOnUi, final OnRequested<T> onRequested) {
        if (workOnUi) {
            mWorkerThread = AndroidSchedulers.mainThread();
        }
        mObservable = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                onRequested.onRequest(subscriber);
            }
        }).materialize().observeOn(mUiThread).<T>dematerialize().subscribeOn(mWorkerThread);
    }

    public ObservableListenerDetacher startRequest(ObservableListener<T> repoListener) {
        Subscription subscription = mObservable.subscribe(repoListener);
        return new ObservableListenerDetacher(subscription);
    }
}
