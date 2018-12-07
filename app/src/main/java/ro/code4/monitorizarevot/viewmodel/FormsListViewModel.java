package ro.code4.monitorizarevot.viewmodel;


import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class FormsListViewModel extends BaseViewModel {

    @Inject
    public FormsListViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
