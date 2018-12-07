package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class QuestionViewModel extends BaseViewModel {

    @Inject
    public QuestionViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
