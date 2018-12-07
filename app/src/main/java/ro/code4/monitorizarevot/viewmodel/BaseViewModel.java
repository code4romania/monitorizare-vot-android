package ro.code4.monitorizarevot.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;
import ro.code4.monitorizarevot.domain.usecase.UseCaseType;
import ro.code4.monitorizarevot.presentation.LoadingMessage;
import ro.code4.monitorizarevot.presentation.LoadingMessageFactory;
import ro.code4.monitorizarevot.presentation.MessageFactory;
import ro.code4.monitorizarevot.presentation.livedata.SingleLiveEvent;

public abstract class BaseViewModel extends ViewModel {

    protected final UseCaseFactory mUseCaseFactory;

    protected final LoadingMessageFactory mLoadingMessageFactory;

    protected final MessageFactory mMessageFactory;

    protected final SingleLiveEvent<String> mMessage = new SingleLiveEvent<>();

    protected final MutableLiveData<LoadingMessage> mContentLoading = new MutableLiveData<>();

    public BaseViewModel(UseCaseFactory useCaseFactory,
                         LoadingMessageFactory loadingMessageFactory,
                         MessageFactory messageFactory) {
        mUseCaseFactory = useCaseFactory;
        mLoadingMessageFactory = loadingMessageFactory;
        mMessageFactory = messageFactory;
    }

    public LiveData<String> message() {
        return mMessage;
    }

    public LiveData<LoadingMessage> contentLoading() {
        return mContentLoading;
    }

    protected LoadingMessage getLoadingMessage(UseCaseType useCaseType) {
        return mLoadingMessageFactory.getMessage(useCaseType);
    }
}
