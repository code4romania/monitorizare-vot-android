package ro.code4.monitorizarevot

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import butterknife.ButterKnife
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_phone_auth.*
import ro.code4.monitorizarevot.constants.Constants.ORGANISATION_WEB_URL
import ro.code4.monitorizarevot.viewmodel.PhoneAuthViewModel
import java.util.concurrent.TimeUnit


class PhoneAuthActivity : BaseActivity<PhoneAuthViewModel>() {


    companion object {
        @JvmStatic
        val TAG: String = PhoneAuthActivity::class.java.simpleName
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")

                        val user = task.result?.user

                        // ...
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            showErrorDialog(task.exception?.localizedMessage)
                        }
                    }
                }
    }

    lateinit var auth: FirebaseAuth
    override fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(PhoneAuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
        ButterKnife.bind(this)


        auth = FirebaseAuth.getInstance()
        auth.useAppLanguage()

        app_version.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        viewModel.message().observe(this, Observer { message -> showErrorDialog(message) })

        viewModel.loginStatus().observe(this, Observer { status ->
            if (status!!) {
                performNavigation()
            }
        })

        viewModel.codeSent().observe(this, Observer {
            loginBtn.text = getString(R.string.login)
            codeLayout.visibility = View.VISIBLE
        })

        viewModel.credential().observe(this, Observer { credential ->
            credential?.let {
                signInWithPhoneAuthCredential(it)
            }
        })

        phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                loginBtn.isEnabled = s.isNotEmpty()
            }

        })

        loginBtn.setOnClickListener {

            handleLoginClick()
        }
        login_organisation_link.setOnClickListener {
            openOrganisationWebpage()
        }

        code.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleLoginClick()
            }
            return@setOnEditorActionListener false
        }

    }

    private fun handleLoginClick() {
        val phoneText = phone.text.toString()
        val codeText = code.text.toString()
        if (codeText.isNotEmpty()) {
            login(phoneText, codeText)
        } else {
            if (phoneText.isNotEmpty()) {
                validatePhoneNumber(phoneText)
            }
        }
    }

    private fun showErrorDialog(message: String?) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(App.getContext(), message, Toast.LENGTH_LONG).show()
        }
    }


    private fun validatePhoneNumber(phoneText: String) {
        //TODO remove hardcoded country code
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber("+4$phoneText", 60, TimeUnit.SECONDS, this, viewModel.callbacks)
    }

    @SuppressLint("HardwareIds")
    private fun login(phoneNumber: String, code: String) {
        val uuid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.login(phoneNumber, code, uuid)
    }


    private fun performNavigation() {
        val intent = Intent(this, ToolbarActivity::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val options: ActivityOptions
            val sharedBackground = Pair<View, String>(findViewById(R.id.purple_background),
                    getString(R.string.shared_element_login_background))
            val sharedLogo = Pair<View, String>(findViewById(R.id.logo), getString(R.string.shared_element_logo))
            options = ActivityOptions
                    .makeSceneTransitionAnimation(this, sharedBackground, sharedLogo)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
        finish() //TODO finish after transition is complete
    }

    private fun openOrganisationWebpage() {
        val openBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(ORGANISATION_WEB_URL))
        startActivity(openBrowser)
    }
}