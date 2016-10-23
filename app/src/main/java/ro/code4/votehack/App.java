package ro.code4.votehack;

import android.app.Application;

import io.realm.Realm;

public class App extends Application {
    public static final String SERVICE_CENTER_PHONE_NUMBER = "0800 080 200";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
