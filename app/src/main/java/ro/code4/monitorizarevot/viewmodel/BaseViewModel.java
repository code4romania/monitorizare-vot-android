package ro.code4.monitorizarevot.viewmodel;

import android.arch.lifecycle.ViewModel;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;

public abstract class BaseViewModel extends ViewModel {

    protected final UseCaseFactory mUseCaseFactory;

    public BaseViewModel(UseCaseFactory useCaseFactory) {
        mUseCaseFactory = useCaseFactory;
    }
}
