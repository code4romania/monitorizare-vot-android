package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class BranchSelectionViewModel extends BaseViewModel {

    @Inject
    public BranchSelectionViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
