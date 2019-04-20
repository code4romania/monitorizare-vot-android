package ro.code4.monitorizarevot.viewmodel

import android.arch.lifecycle.LiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory
import ro.code4.monitorizarevot.presentation.LoadingMessage
import ro.code4.monitorizarevot.presentation.LoadingMessageFactory
import ro.code4.monitorizarevot.presentation.Message
import ro.code4.monitorizarevot.presentation.MessageFactory
import ro.code4.monitorizarevot.presentation.livedata.SingleLiveEvent
import ro.code4.monitorizarevot.presentation.rx.DefaultObserver
import ro.code4.monitorizarevot.R
import javax.inject.Inject

class PhoneAuthViewModel @Inject
constructor(useCaseFactory: UseCaseFactory, loadingMessageFactory: LoadingMessageFactory, messageFactory: MessageFactory) : BaseViewModel(useCaseFactory, loadingMessageFactory, messageFactory) {

    private val mLoginStatus = SingleLiveEvent<Boolean>()
    private val codeSentLiveData = SingleLiveEvent<Boolean>()
    private val credentialLiveData = SingleLiveEvent<PhoneAuthCredential>()
    private val messageIdLiveData = SingleLiveEvent<Int>()
    private val validatePhoneLiveData = SingleLiveEvent<String>()

    var verificationId: String? = null

    val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
                credentialLiveData.postValue(credential)
                mContentLoading.postValue(LoadingMessage(false))
            }

            override fun onVerificationFailed(exception: FirebaseException?) {
                mMessage.postValue(exception?.localizedMessage)
                mContentLoading.postValue(LoadingMessage(false))
            }

            override fun onCodeSent(vId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                verificationId = vId
                codeSentLiveData.postValue(true)
                mContentLoading.postValue(LoadingMessage(false))
            }

        }
    }

    fun codeSent(): LiveData<Boolean> = codeSentLiveData
    fun credential(): LiveData<PhoneAuthCredential> = credentialLiveData
    fun loginStatus(): LiveData<Boolean> = mLoginStatus
    fun messageId(): LiveData<Int> = messageIdLiveData
    fun validatePhone(): LiveData<String> = validatePhoneLiveData

    fun login(code: String) {
        mContentLoading.postValue(LoadingMessage(true))
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        credentialLiveData.postValue(credential)
    }

    fun notifyUserSignedIn(user: FirebaseUser?) {
        //TODO
        //Request an authorization token from backend for the successfully signed in user
        //mLoginStatus.postValue(true)
    }

    fun resetCode() {
        verificationId = null
    }

    private fun hasVerificationId(): Boolean = verificationId != null
    fun handleLoginClick(phoneNumber: String, code: String) {

        val shouldLogin = hasVerificationId()
        when {
            !shouldLogin -> {
                //TODO check if the phone number exists in the database and then proceed to verifying the phone number with Firebase
                //TODO remove hardcoded country code
                mContentLoading.postValue(LoadingMessage(true))
                validatePhoneLiveData.postValue("+4$phoneNumber")
            }
            shouldLogin && code.isEmpty() -> messageIdLiveData.postValue(R.string.error_empty_code)
            shouldLogin && code.isNotEmpty() -> login(code)
        }

    }

    private inner class LoginObserver(messageFactory: MessageFactory) : DefaultObserver<Boolean>(messageFactory) {

        override fun onNext(success: Boolean?) {
            mLoginStatus.postValue(success)
            mContentLoading.postValue(LoadingMessage(false))
        }

        override fun onErrorMessage(message: Message) {
            mMessage.postValue(message.message)
            mContentLoading.postValue(LoadingMessage(false))
        }
    }
}
