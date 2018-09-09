package ro.code4.monitorizarevot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.observable.ObservableListener;
import ro.code4.monitorizarevot.observable.ObservableListenerDetacher;
import vn.tungdx.mediapicker.activities.MediaPickerErrorDialog;

import static ro.code4.monitorizarevot.constants.Constants.ORGANISATION_WEB_URL;

public class LoginActivity extends BaseActivity {
    private EditText username;
    private EditText password;
    private ObservableListenerDetacher mListenerDetacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.phone);
        password = findViewById(R.id.branch);
        username.getText();

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        View organisationLink = findViewById(R.id.login_organisation_link);
        organisationLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrganisationWebpage();
            }
        });

        setAppVersion((TextView) findViewById(R.id.app_version));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mListenerDetacher != null && !mListenerDetacher.isDetached()){
            mListenerDetacher.detach();
        }
    }

    private void openOrganisationWebpage() {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(ORGANISATION_WEB_URL));
        startActivity(openBrowser);
    }

    private void setAppVersion(TextView appVersion) {
        appVersion.setText(getString(R.string.app_version, BuildConfig.VERSION_NAME));
    }

    private void login() {
        String userName = username.getText().toString();
        String pass = password.getText().toString();

        if(!TextUtils.isEmpty(userName.trim()) && !TextUtils.isEmpty(pass.trim())){
            showLoading();
            User user = new User(userName, pass, getUdid());
            mListenerDetacher = NetworkService.login(user).startRequest(new LoginSubscriber());
        } else {
            MediaPickerErrorDialog
                    .newInstance(getString(R.string.empty_credential_message))
                    .show(getSupportFragmentManager(), null);
        }
    }

    private String getUdid() {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private class LoginSubscriber extends ObservableListener<Boolean> {
        @Override
        public void onError(Throwable e) {
            super.onError(e);
            hideLoading();
            MediaPickerErrorDialog
                    .newInstance(e.getMessage())
                    .show(getSupportFragmentManager(), null);
        }

        @Override
        public void onSuccess() {
            hideLoading();
            Intent intent = new Intent(LoginActivity.this, ToolbarActivity.class);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options;
                Pair<View, String> sharedBackground = new Pair<>(findViewById(R.id.purple_background), getString(R.string.shared_element_login_background));
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
}

