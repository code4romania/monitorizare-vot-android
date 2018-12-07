package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class BranchDetailsViewModel extends BaseViewModel {

    @Inject
    public BranchDetailsViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
