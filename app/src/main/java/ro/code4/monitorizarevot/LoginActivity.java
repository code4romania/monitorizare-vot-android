package ro.code4.monitorizarevot;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.code4.monitorizarevot.viewmodel.LoginViewModel;
import vn.tungdx.mediapicker.activities.MediaPickerErrorDialog;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    @BindView(R.id.phone)
    EditText username;

    @BindView(R.id.branch)
    EditText password;

    @BindView(R.id.app_version)
    TextView appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        appVersion.setText(getString(R.string.app_version, BuildConfig.VERSION_NAME));

        viewModel.message().observe(this, new Observer<String>() {

            @Override
            public void onChanged(@Nullable String message) {
                showErrorDialog(message);
            }
        });

        viewModel.loginStatus().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    performNavigation();
                }
            }
        });



        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                   login();
                }
                return false;
            }
        });
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @OnClick(R.id.login_button)
    void onLoginButtonClick() {
        login();
    }

    private void login(){
        String phoneNumber = username.getText().toString();
        String pin = password.getText().toString();
        String udid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        viewModel.login(phoneNumber, pin, udid);
    }

    private void showErrorDialog(String message) {
        if (!TextUtils.isEmpty(message)) {
            MediaPickerErrorDialog.newInstance(message).show(getSupportFragmentManager(), null);
        }
    }

    private void performNavigation() {
        Intent intent = new Intent(LoginActivity.this, ToolbarActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options;
            Pair<View, String> sharedBackground = new Pair<>(findViewById(R.id.purple_background),
                                                             getString(R.string.shared_element_login_background));
            Pair<View, String> sharedLogo = new Pair<>(findViewById(R.id.logo), getString(R.string.shared_element_logo));
            options = ActivityOptions
                    .makeSceneTransitionAnimation(LoginActivity.this, sharedBackground, sharedLogo);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
        finish(); //TODO finish after transition is complete
    }
}

