package ro.code4.monitorizarevot;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.observable.ObservableListener;
import ro.code4.monitorizarevot.viewmodel.StartViewModel;

public class StartActivity extends BaseActivity<StartViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Preferences.isAlreadyLoggedIn().startRequest(new PreferencesSubscriber());
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(StartViewModel.class);
    }

    private class PreferencesSubscriber extends ObservableListener<Boolean> {

        @Override
        public void onNext(Boolean hasCredentials) {
            super.onNext(hasCredentials);
            startActivity(new Intent(StartActivity.this,
                                     hasCredentials ? ToolbarActivity.class : LoginActivity.class));
        }

        @Override
        public void onSuccess() {

        }
    }
}
