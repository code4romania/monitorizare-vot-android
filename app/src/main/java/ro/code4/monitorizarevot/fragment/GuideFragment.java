package ro.code4.monitorizarevot.fragment;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;

public class GuideFragment extends BaseFragment {
    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_guide);
    }
}
