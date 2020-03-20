package ro.code4.monitorizarevot;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ro.code4.monitorizarevot.adapter.OnboardingAdapter;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.OnboardingItem;
import ro.code4.monitorizarevot.observable.ObservableListener;
import ro.code4.monitorizarevot.viewmodel.OnboardingViewModel;

import ro.code4.monitorizarevot.R;

public class OnboardingActivity extends BaseActivity<OnboardingViewModel> {
    private Button buttonPrevious, buttonNext;

    private List<OnboardingItem> items;

    private ViewPager onboardingViewPager;
    private int currentPosition;

    // TODO get onboarding items dynamically
    private List<OnboardingItem> getOnboardingItems() {
        List<OnboardingItem> items = new ArrayList<>();

        OnboardingItem item1 = new OnboardingItem(1, getString(R.string.onboarding_title_1), getString(R.string.onboarding_description_1), R.drawable.ic_onboarding_building);
        items.add(item1);

        OnboardingItem item2 = new OnboardingItem(2, getString(R.string.onboarding_title_2), getString(R.string.onboarding_description_2), R.drawable.ic_onboarding_form);
        items.add(item2);

        OnboardingItem item3 = new OnboardingItem(3, getString(R.string.onboarding_title_3), getString(R.string.onboarding_description_3), R.drawable.ic_onboarding_note);
        items.add(item3);

        return items;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        items = getOnboardingItems();

        onboardingViewPager = findViewById(R.id.onboarding_viewpager);
        buttonNext = findViewById(R.id.btn_next);
        buttonPrevious = findViewById(R.id.btn_previous);

        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(this, items);
        onboardingViewPager.setAdapter(onboardingAdapter);
        onboardingViewPager.addOnPageChangeListener(onPageChangeListener);

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onboardingViewPager.setCurrentItem(currentPosition - 1);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == items.size() - 1) {
                    goToLogin();
                } else {
                    onboardingViewPager.setCurrentItem(currentPosition + 1);
                }
            }
        });

        Preferences.hasSeenOnboarding().startRequest(new PreferencesSubscriber());
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(OnboardingViewModel.class);
    }

    private class PreferencesSubscriber extends ObservableListener<Boolean> {

        @Override
        public void onNext(Boolean hasSeenOnboarding) {
            super.onNext(hasSeenOnboarding);
            if (hasSeenOnboarding) {
                goToLogin();
            }
        }

        @Override
        public void onSuccess() {

        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;

            if (position == 0) {
                buttonPrevious.setVisibility(View.INVISIBLE);
            } else if (position == items.size() - 1) {
                buttonNext.setText(getString(R.string.onboarding_continue));
            } else {
                buttonPrevious.setVisibility(View.VISIBLE);

                buttonNext.setText(getString(R.string.onboarding_next));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void goToLogin() {
        Preferences.saveSeenOnboarding(true);
        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }
}
