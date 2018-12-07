package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class GuideViewModel extends BaseViewModel {

    @Inject
    public GuideViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
