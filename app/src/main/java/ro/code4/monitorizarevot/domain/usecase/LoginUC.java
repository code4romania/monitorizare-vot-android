package ro.code4.monitorizarevot.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;

import ro.code4.monitorizarevot.data.repository.RepositoryFactory;
import ro.code4.monitorizarevot.domain.params.LoginDataParams;
import rx.Observable;
import rx.Scheduler;

public class LoginUC extends UseCase<Boolean, LoginDataParams> {

    @Inject
    public LoginUC(@Named("io") Scheduler subscribeScheduler,
                   @Named("main") Scheduler observeScheduler, RepositoryFactory repoFactory) {
        super(UseCaseType.LOGIN, subscribeScheduler, observeScheduler, repoFactory);
    }

    @Override
    protected Observable<Boolean> buildObservable(LoginDataParams params) {
        return mRepoFactory.auth().login(params);
    }
}
