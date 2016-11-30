package ro.code4.votehack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class StartActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO
        // if logged in
        //   start ToolbarActivity
        // else
        startActivity(new Intent(this, LoginActivity.class));
    }
}
