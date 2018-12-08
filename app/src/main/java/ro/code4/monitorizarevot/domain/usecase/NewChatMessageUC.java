package ro.code4.monitorizarevot.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;

import ro.code4.monitorizarevot.data.chat.ChatMessage;
import ro.code4.monitorizarevot.data.repository.RepositoryFactory;
import rx.Observable;
import rx.Scheduler;

import static ro.code4.monitorizarevot.domain.usecase.UseCaseType.NEW_MESSAGE;

public class NewChatMessageUC extends UseCase<ChatMessage, Void> {

    @Inject
    public NewChatMessageUC(@Named("io") Scheduler subscribeScheduler, @Named("main") Scheduler observeScheduler,
                            RepositoryFactory repoFactory) {
        super(NEW_MESSAGE, subscribeScheduler, observeScheduler, repoFactory);
    }

    @Override
    protected Observable<ChatMessage> buildObservable(Void aVoid) {
        return mRepoFactory.chat().newMessage();
    }
}
