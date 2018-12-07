package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.viewmodel.GuideViewModel;

public class GuideFragment extends BaseFragment<GuideViewModel> {

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView guideView = (WebView) view.findViewById(R.id.web_view_guide);
        WebSettings webSettings = guideView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        guideView.loadUrl("http://monitorizare-vot-ghid.azurewebsites.net/");
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
