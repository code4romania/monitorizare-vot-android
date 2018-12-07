package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class LoginViewModel extends BaseViewModel {

    @Inject
    public LoginViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
