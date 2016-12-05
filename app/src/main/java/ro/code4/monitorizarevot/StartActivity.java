package ro.code4.monitorizarevot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ro.code4.monitorizarevot.db.Preferences;

public class StartActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, Preferences.hasCredentials() ? ToolbarActivity.class : LoginActivity.class));
    }
}
