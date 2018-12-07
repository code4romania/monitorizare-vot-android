package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class QuestionOverviewViewModel extends BaseViewModel {

    @Inject
    public QuestionOverviewViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
