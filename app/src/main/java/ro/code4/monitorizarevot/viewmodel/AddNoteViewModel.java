package ro.code4.monitorizarevot.viewmodel;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public class AddNoteViewModel extends BaseViewModel {

    @Inject
    public AddNoteViewModel(UseCaseFactory useCaseFactory) {
        super(useCaseFactory);
    }
}
