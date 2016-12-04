package ro.code4.monitorizarevot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;

public class GuideFragment extends BaseFragment {
    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_guide);
    }
}
