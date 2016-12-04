package ro.code4.monitorizarevot;

import android.app.ActivityOptions;
import android.content.Intent;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {
    private EditText username;
    private EditText password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.branch);
        username.getText();

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        setAppVersion((TextView) findViewById(R.id.app_version));
    }

    private void setAppVersion(TextView appVersion) {
        appVersion.setText(getString(R.string.app_version, BuildConfig.VERSION_NAME));
    }

    private void login() {
        Intent intent = new Intent(this, ToolbarActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options;
            Pair<View, String> sharedBackground = new Pair<>(findViewById(R.id.purple_background), getString(R.string.shared_element_login_background));
            Pair<View, String> sharedLogo = new Pair<>(findViewById(R.id.logo), getString(R.string.shared_element_logo));
            options = ActivityOptions
                    .makeSceneTransitionAnimation(this, sharedBackground, sharedLogo);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
        finish(); //TODO finish after transition is complete
    }
}

