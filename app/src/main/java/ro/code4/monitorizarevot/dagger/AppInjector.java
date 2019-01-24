package ro.code4.monitorizarevot.dagger;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import ro.code4.monitorizarevot.App;
import ro.code4.monitorizarevot.Injectable;

/**
 * Component that initializes the application's dependency graph (sets up the app's dagger component).
 */
public class AppInjector {

    private AppInjector() {
    }

    public static void init(App application) {
        DaggerAppComponent.builder().application(application).build().inject(application);

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void handleActivity(Activity activity) {
        AndroidInjection.inject(activity);

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                                         .registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {

                                             @Override
                                             public void onFragmentCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment,
                                                                           Bundle savedInstanceState) {
                                                 if (fragment instanceof Injectable) {
                                                     AndroidSupportInjection.inject(fragment);
                                                 }
                                             }
                                         }, true);
        }
    }
}
