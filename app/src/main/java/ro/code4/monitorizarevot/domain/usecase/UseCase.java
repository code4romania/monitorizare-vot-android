package ro.code4.monitorizarevot.domain.usecase;

import ro.code4.monitorizarevot.data.repository.RepositoryFactory;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;

public abstract class UseCase<T, Params> {

    protected final RepositoryFactory mRepoFactory;

    private final UseCaseType mType;

    private final Scheduler mSubscribeScheduler;

    private final Scheduler mObserveScheduler;

    private Subscription mSubscription;

    UseCase(UseCaseType type, Scheduler subscribeScheduler, Scheduler observeScheduler, RepositoryFactory repoFactory) {
        mType = type;

        mSubscribeScheduler = subscribeScheduler;
        mObserveScheduler = observeScheduler;

        mRepoFactory = repoFactory;
    }

    public void execute(Observer<T> observer) {
        execute(observer, null);
    }

    public void execute(Observer<T> observer, Params params) {
        dispose();

        final Observable<T> observable = buildObservable(params).subscribeOn(mSubscribeScheduler).observeOn(mObserveScheduler);

        mSubscription = observable.subscribe(observer);
    }

    public UseCaseType getType() {
        return mType;
    }

    public void dispose() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    protected abstract Observable<T> buildObservable(Params params);
}
