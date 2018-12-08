package ro.code4.monitorizarevot.data.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.domain.repository.AuthRepository;
import ro.code4.monitorizarevot.domain.repository.ChatRepository;
import ro.code4.monitorizarevot.domain.repository.DataRepository;
import ro.code4.monitorizarevot.domain.repository.FormsRepository;
import ro.code4.monitorizarevot.util.AbstractComponentFactory;

@Singleton
public class RepositoryFactory extends AbstractComponentFactory<DataRepository> {

    @Inject
    public RepositoryFactory(Map<Class<? extends DataRepository>, Provider<DataRepository>> creators) {
        super(creators);
    }

    public AuthRepository auth() {
        return create(AuthRepositoryImpl.class);
    }

    public FormsRepository forms() {
        return create(FormsRepositoryImpl.class);
    }

    public ChatRepository chat() {
        return create(ChatRepositoryImpl.class);
    }
}
