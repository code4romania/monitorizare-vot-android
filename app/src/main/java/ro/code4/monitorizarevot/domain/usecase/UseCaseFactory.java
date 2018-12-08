package ro.code4.monitorizarevot.domain.usecase;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.util.AbstractComponentFactory;

@Singleton
public class UseCaseFactory extends AbstractComponentFactory<UseCase> {

    @Inject
    public UseCaseFactory(Map<Class<? extends UseCase>, Provider<UseCase>> creators) {
        super(creators);
    }

    public LoginUC login() {
        return create(LoginUC.class);
    }

    public ChatMessageHistoryUC chatHistory() {
        return create(ChatMessageHistoryUC.class);
    }

    public NewChatMessageUC messageReceiver() {
        return create(NewChatMessageUC.class);
    }

    public SendChatMessageUC messageSender() {
        return create(SendChatMessageUC.class);
    }
}
