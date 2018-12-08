package ro.code4.monitorizarevot.domain.usecase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ro.code4.monitorizarevot.data.chat.ChatMessage;
import ro.code4.monitorizarevot.data.repository.RepositoryFactory;
import rx.Observable;
import rx.Scheduler;

import static ro.code4.monitorizarevot.domain.usecase.UseCaseType.CHAT_MESSAGE_HISTORY;

public class ChatMessageHistoryUC extends UseCase<List<ChatMessage>, Void> {

    @Inject
    public ChatMessageHistoryUC(@Named("io") Scheduler subscribeScheduler, @Named("main") Scheduler observeScheduler,
                                RepositoryFactory repoFactory) {
        super(CHAT_MESSAGE_HISTORY, subscribeScheduler, observeScheduler, repoFactory);
    }

    @Override
    protected Observable<List<ChatMessage>> buildObservable(Void aVoid) {
        return mRepoFactory.chat().history();
    }
}
