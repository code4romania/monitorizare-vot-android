package ro.code4.votehack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import io.realm.Realm;

public abstract class BaseFragment extends Fragment implements Navigator {
    private Navigator navigator;
    private Realm realm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Navigator)) {
            throw new IllegalStateException("Must be attached to an activity implementing Navigator");
        } else {
            this.navigator = (Navigator) context;
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
        setTitle(getTitle());
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
    public void setTitle(String title) {
        navigator.setTitle(title);
    }

    public abstract String getTitle();
}
