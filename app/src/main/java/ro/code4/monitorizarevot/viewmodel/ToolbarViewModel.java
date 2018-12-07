package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class ToolbarViewModel extends BaseViewModel {

    @Inject
    public ToolbarViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
