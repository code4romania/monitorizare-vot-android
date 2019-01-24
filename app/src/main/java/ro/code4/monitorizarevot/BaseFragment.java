package ro.code4.monitorizarevot;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

import io.realm.Realm;
import ro.code4.monitorizarevot.util.ActivityOperations;
import ro.code4.monitorizarevot.viewmodel.BaseViewModel;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment implements Navigator, ActivityOperations, Injectable {

    protected VM viewModel;

    @Inject
    protected ViewModelProvider.Factory factory;

    private Navigator navigator;

    private ActivityOperations operations;

    private Realm realm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Navigator)) {
            throw new IllegalStateException("Must be attached to an activity implementing Navigator");
        } else {
            this.navigator = (Navigator) context;
        }
        if (!(context instanceof ActivityOperations)) {
            throw new IllegalStateException("Must be attached to an activity implementing ActivityOperations");
        } else {
            this.operations = (ActivityOperations) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setTitle(getTitle());
        setMenu(withMenu());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void navigateTo(BaseFragment fragment) {
        navigator.navigateTo(fragment);
    }

    @Override
    public void navigateTo(BaseFragment fragment, boolean addToBackStack) {
        navigator.navigateTo(fragment, addToBackStack);
    }

    @Override
    public void navigateBack() {
        navigator.navigateBack();
    }

    @Override
    public void navigateBackUntil(int backstackIndex) {
        navigator.navigateBackUntil(backstackIndex);
    }

    public abstract String getTitle();

    @Override
    public void setTitle(String title) {
        navigator.setTitle(title);
    }

    @Override
    public void setMenu(boolean isEnabled) {
        navigator.setMenu(isEnabled);
    }

    /**
     * Return false from subclass to hide the hamburger icon
     */
    public boolean withMenu() {
        return true;
    }

    protected boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(getActivity(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    protected boolean hasGrantedPermission(@NonNull int[] grantResults) {
        return grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(),
                                          new String[]{permission},
                                          requestCode);
    }

    @Override
    public void hideFocusedKeyboard() {
        operations.hideFocusedKeyboard();
    }

    protected abstract void setupViewModel();
}
