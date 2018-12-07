package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class StartViewModel extends BaseViewModel {

    @Inject
    public StartViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
