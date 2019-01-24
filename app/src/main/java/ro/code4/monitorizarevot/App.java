package ro.code4.monitorizarevot;

import com.pixplicity.easyprefs.library.Prefs;

import net.hockeyapp.android.CrashManager;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasServiceInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ro.code4.monitorizarevot.dagger.AppInjector;

public class App extends Application implements HasActivityInjector, HasBroadcastReceiverInjector, HasServiceInjector {

    private static Context mContext;

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> mDispatchingBroadcastReceiverInjector;

    @Inject
    DispatchingAndroidInjector<Service> mDispatchingServiceInjector;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppInjector.init(this);

        mContext = getApplicationContext();
        CrashManager.register(this);

        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration
                .Builder()
                                              .deleteRealmIfMigrationNeeded()
                                              .build());

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return mDispatchingBroadcastReceiverInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return mDispatchingServiceInjector;
    }
}
