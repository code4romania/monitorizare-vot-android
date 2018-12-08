package ro.code4.monitorizarevot.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import ro.code4.monitorizarevot.data.repository.AuthRepositoryImpl;
import ro.code4.monitorizarevot.data.repository.ChatRepositoryImpl;
import ro.code4.monitorizarevot.data.repository.FormsRepositoryImpl;
import ro.code4.monitorizarevot.domain.repository.DataRepository;
import ro.code4.monitorizarevot.domain.usecase.ChatMessageHistoryUC;
import ro.code4.monitorizarevot.domain.usecase.LoginUC;
import ro.code4.monitorizarevot.domain.usecase.NewChatMessageUC;
import ro.code4.monitorizarevot.domain.usecase.SendChatMessageUC;
import ro.code4.monitorizarevot.domain.usecase.UseCase;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public abstract class DomainModule {

    @Provides
    @Singleton
    @Named("io")
    static Scheduler provideIOScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Named("main")
    static Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Binds
    @IntoMap
    @DataRepositoryKey(AuthRepositoryImpl.class)
    abstract DataRepository bindAuthRepository(AuthRepositoryImpl authRepository);

    @Binds
    @IntoMap
    @DataRepositoryKey(FormsRepositoryImpl.class)
    abstract DataRepository bindEventRepository(FormsRepositoryImpl formsRepository);

    @Binds
    @IntoMap
    @DataRepositoryKey(ChatRepositoryImpl.class)
    abstract DataRepository bindChatRepository(ChatRepositoryImpl chatRepository);

    @Binds
    @IntoMap
    @UseCaseKey(LoginUC.class)
    abstract UseCase bindLoginUC(LoginUC useCase);

    @Binds
    @IntoMap
    @UseCaseKey(ChatMessageHistoryUC.class)
    abstract UseCase bindChatMessageHistoryUC(ChatMessageHistoryUC useCase);

    @Binds
    @IntoMap
    @UseCaseKey(NewChatMessageUC.class)
    abstract UseCase bindNewChatMessageUC(NewChatMessageUC useCase);

    @Binds
    @IntoMap
    @UseCaseKey(SendChatMessageUC.class)
    abstract UseCase bindSendChatMessageUC(SendChatMessageUC useCase);
}
