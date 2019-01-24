package ro.code4.monitorizarevot.viewmodel;

import android.arch.lifecycle.LiveData;

import javax.inject.Inject;

import ro.code4.monitorizarevot.domain.params.LoginDataParams;
import ro.code4.monitorizarevot.domain.usecase.LoginUC;
import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;
import ro.code4.monitorizarevot.presentation.LoadingMessage;
import ro.code4.monitorizarevot.presentation.LoadingMessageFactory;
import ro.code4.monitorizarevot.presentation.Message;
import ro.code4.monitorizarevot.presentation.MessageFactory;
import ro.code4.monitorizarevot.presentation.livedata.SingleLiveEvent;
import ro.code4.monitorizarevot.presentation.rx.DefaultObserver;

public class LoginViewModel extends BaseViewModel {

    private SingleLiveEvent<Boolean> mLoginStatus = new SingleLiveEvent<>();

    @Inject
    public LoginViewModel(UseCaseFactory useCaseFactory, LoadingMessageFactory loadingMessageFactory, MessageFactory messageFactory) {
        super(useCaseFactory, loadingMessageFactory, messageFactory);
    }

    public LiveData<Boolean> loginStatus() {
        return mLoginStatus;
    }

    public void login(String phoneNumber, String pin, String udid) {
        LoginUC loginUC = mUseCaseFactory.login();

        mContentLoading.postValue(getLoadingMessage(loginUC.getType()));

        LoginDataParams params = new LoginDataParams(phoneNumber, pin, udid);
        loginUC.execute(new LoginObserver(mMessageFactory), params);
    }

    private class LoginObserver extends DefaultObserver<Boolean> {

        public LoginObserver(MessageFactory messageFactory) {
            super(messageFactory);
        }

        @Override
        public void onNext(Boolean success) {
            mLoginStatus.postValue(success);
            mContentLoading.postValue(new LoadingMessage(false));
        }

        @Override
        public void onErrorMessage(Message message) {
            mMessage.postValue(message.getMessage());
            mContentLoading.postValue(new LoadingMessage(false));
        }
    }
}
