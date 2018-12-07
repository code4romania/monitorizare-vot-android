package ro.code4.monitorizarevot;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.observable.ObservableListener;
import ro.code4.monitorizarevot.observable.ObservableListenerDetacher;
import vn.tungdx.mediapicker.activities.MediaPickerErrorDialog;

public class LoginActivity extends BaseActivity {
    private EditText username;
    private EditText password;
    private Button loginButton;
    private ObservableListenerDetacher mListenerDetacher;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    private String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.branch);
        username.getText();

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    login();
                }
                return false;
            }
        });

        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {
           setPhone();
        }


        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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


    private void setPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, wantPermission) == PackageManager.PERMISSION_GRANTED) {
            if (phoneMgr.getLine1Number() != null && !phoneMgr.getLine1Number().isEmpty()) {
                username.setText(phoneMgr.getLine1Number());
            }
        }else {
            requestPermission(wantPermission);
        }
    }

    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            Toast.makeText(this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setPhone();
                } else {
                    Toast.makeText(this,"Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
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

