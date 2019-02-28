package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.viewmodel.GuideViewModel;
import ro.code4.monitorizarevot.constants.Constants;

public class GuideFragment extends BaseFragment<GuideViewModel> {

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @BindView(R.id.guide_web_view)
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        webView.loadUrl(Constants.GUIDE_URL);
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_guide);
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(GuideViewModel.class);
    }
}
