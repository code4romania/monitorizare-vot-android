package ro.code4.monitorizarevot.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import javax.inject.Inject;

import ro.code4.monitorizarevot.data.chat.ChatMessage;
import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;
import ro.code4.monitorizarevot.presentation.LoadingMessageFactory;
import ro.code4.monitorizarevot.presentation.Message;
import ro.code4.monitorizarevot.presentation.MessageFactory;
import ro.code4.monitorizarevot.presentation.rx.DefaultObserver;

public class ChatViewModel extends BaseViewModel implements LifecycleObserver {

    private MutableLiveData<ChatMessage> mNewMessage = new MutableLiveData<>();

    private MutableLiveData<List<ChatMessage>> mHistory = new MutableLiveData<>();

    @Inject
    public ChatViewModel(UseCaseFactory useCaseFactory,
                         LoadingMessageFactory loadingMessageFactory,
                         MessageFactory messageFactory) {
        super(useCaseFactory, loadingMessageFactory, messageFactory);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onChatStart() {
        mUseCaseFactory.chatHistory().execute(new MessageHistoryObserver(mMessageFactory));
        mUseCaseFactory.messageReceiver().execute(new MessageReceiveObserver(mMessageFactory));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onChatStop() {
        mUseCaseFactory.chatHistory().dispose();
        mUseCaseFactory.messageReceiver().dispose();
    }

    public LiveData<ChatMessage> newMessage() {
        return mNewMessage;
    }

    public LiveData<List<ChatMessage>> history() {
        return mHistory;
    }

    public void sendMessage(String message) {
        mUseCaseFactory.messageSender().execute(new MessageSendObserver(mMessageFactory), message);
    }

    private class MessageReceiveObserver extends DefaultObserver<ChatMessage> {

        public MessageReceiveObserver(MessageFactory messageFactory) {
            super(messageFactory);
        }

        @Override
        public void onNext(ChatMessage message) {
            mNewMessage.postValue(message);
        }

        @Override
        public void onErrorMessage(Message message) {
        }
    }

    private class MessageHistoryObserver extends DefaultObserver<List<ChatMessage>> {

        public MessageHistoryObserver(MessageFactory messageFactory) {
            super(messageFactory);
        }

        @Override
        public void onNext(List<ChatMessage> chatMessageList) {
            mHistory.postValue(chatMessageList);
        }

        @Override
        public void onErrorMessage(ro.code4.monitorizarevot.presentation.Message message) {
        }
    }

    private class MessageSendObserver extends DefaultObserver<Boolean> {

        public MessageSendObserver(MessageFactory messageFactory) {
            super(messageFactory);
        }

        @Override
        public void onNext(Boolean aBoolean) {
        }

        @Override
        public void onErrorMessage(Message message) {
            super.onErrorMessage(message);
        }
    }
}
