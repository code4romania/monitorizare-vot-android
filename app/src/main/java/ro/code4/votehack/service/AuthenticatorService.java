package ro.code4.votehack.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ro.code4.votehack.util.StubAuthenticator;

public class AuthenticatorService extends Service {
    private StubAuthenticator authenticator;

    @Override
    public void onCreate() {
        authenticator = new StubAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
