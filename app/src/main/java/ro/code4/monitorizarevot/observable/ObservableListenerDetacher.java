package ro.code4.monitorizarevot.observable;

import rx.Subscription;

/**
 * A wrapper for the Subscription interface.
 */
public class ObservableListenerDetacher {
    private Subscription mSubscription;

    public ObservableListenerDetacher(Subscription subscription) {
        mSubscription = subscription;
    }

    public void detach() {
        mSubscription.unsubscribe();
    }

    public boolean isDetached() {
        return mSubscription.isUnsubscribed();
    }
}
