package ro.code4.monitorizarevot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import javax.inject.Inject;

import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.LogoutListener;
import ro.code4.monitorizarevot.presentation.LoadingMessage;
import ro.code4.monitorizarevot.util.ActivityOperations;
import ro.code4.monitorizarevot.util.AuthUtils;
import ro.code4.monitorizarevot.viewmodel.BaseViewModel;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements ActivityOperations {

    protected VM viewModel;

    @Inject
    protected ViewModelProvider.Factory factory;

    private ProgressDialog loadingIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViewModel();

        viewModel.contentLoading().observe(this, new Observer<LoadingMessage>() {

            @Override
            public void onChanged(@Nullable LoadingMessage loadingMessage) {
                showHideLoading(loadingMessage);
            }
        });

        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LogoutListener event) {
        AuthUtils.removeAccountAndStopSync();
        Preferences.clear();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        this.finish();
    }

    public void hideFocusedKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected void showHideLoading(LoadingMessage loadingMessage) {
        if (loadingMessage != null) {
            if (loadingMessage.isLoading()) {
                loadingIndicator.setMessage(loadingMessage.getMessage());
                loadingIndicator.show();

            } else {
                loadingIndicator.hide();
            }
        }
    }

    protected abstract void setupViewModel();
}
