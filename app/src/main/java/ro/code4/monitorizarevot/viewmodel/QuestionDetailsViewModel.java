package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class QuestionDetailsViewModel extends BaseViewModel {

    @Inject
    public QuestionDetailsViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
