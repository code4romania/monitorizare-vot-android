package ro.code4.votehack;

import android.app.Fragment;
import android.content.Context;

public abstract class BaseFragment extends Fragment implements Navigator {
    public abstract String getIdentifier();
    private Navigator navigator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Navigator)) {
            throw new IllegalStateException("Must be attached to an activity implementing Navigator");
        } else {
            this.navigator = (Navigator) getActivity();
        }
    }

    @Override
    public void navigateTo(BaseFragment fragment) {
        navigator.navigateTo(fragment);
    }

    @Override
    public void navigateTo(BaseFragment fragment, boolean addToBackStack) {
        navigator.navigateTo(fragment, addToBackStack);
    }
}
