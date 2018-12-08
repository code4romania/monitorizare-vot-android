package ro.code4.monitorizarevot.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;

import ro.code4.monitorizarevot.data.repository.RepositoryFactory;
import rx.Observable;
import rx.Scheduler;

import static ro.code4.monitorizarevot.domain.usecase.UseCaseType.SEND_MESSAGE;

public class SendChatMessageUC extends UseCase<Boolean, String> {

    @Inject
    public SendChatMessageUC(@Named("io") Scheduler subscribeScheduler, @Named("main") Scheduler observeScheduler,
                             RepositoryFactory repoFactory) {
        super(SEND_MESSAGE, subscribeScheduler, observeScheduler, repoFactory);
    }

    @Override
    protected Observable<Boolean> buildObservable(String message) {
        return mRepoFactory.chat().sendMessage(message);
    }
}
