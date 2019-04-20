package ro.code4.monitorizarevot.viewmodel

import android.arch.lifecycle.LiveData
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory
import ro.code4.monitorizarevot.presentation.LoadingMessage
import ro.code4.monitorizarevot.presentation.LoadingMessageFactory
import ro.code4.monitorizarevot.presentation.Message
import ro.code4.monitorizarevot.presentation.MessageFactory
import ro.code4.monitorizarevot.presentation.livedata.SingleLiveEvent
import ro.code4.monitorizarevot.presentation.rx.DefaultObserver
import javax.inject.Inject

class PhoneAuthViewModel @Inject
constructor(useCaseFactory: UseCaseFactory, loadingMessageFactory: LoadingMessageFactory, messageFactory: MessageFactory) : BaseViewModel(useCaseFactory, loadingMessageFactory, messageFactory) {

    private val mLoginStatus = SingleLiveEvent<Boolean>()
    private val codeSentLiveData = SingleLiveEvent<Boolean>()
    private val credentialLiveData = SingleLiveEvent<PhoneAuthCredential>()
    var verificationId: String? = null

    val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
//                 login(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException?) {
                mMessage.postValue(exception?.localizedMessage)
            }

            override fun onCodeSent(vId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                Log.i("phoneauth", vId)
                verificationId = vId
                codeSentLiveData.postValue(true)
            }

        }
    }

    fun codeSent(): LiveData<Boolean> = codeSentLiveData
    fun credential(): LiveData<PhoneAuthCredential> = credentialLiveData
    fun loginStatus(): LiveData<Boolean> = mLoginStatus

    fun login(phoneNumber: String, code: String, uuid: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        credentialLiveData.postValue(credential)
//        val loginUC = mUseCaseFactory.login()
//
//        mContentLoading.postValue(getLoadingMessage(loginUC.type))
//
//        val params = LoginDataParams(phoneNumber, code, uuid)
//        loginUC.execute(LoginObserver(mMessageFactory), params)
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
