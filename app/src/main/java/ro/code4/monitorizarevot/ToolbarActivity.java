package ro.code4.monitorizarevot;

import org.greenrobot.eventbus.EventBus;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ro.code4.monitorizarevot.adapter.SyncAdapter;
import ro.code4.monitorizarevot.constants.Constants;
import ro.code4.monitorizarevot.fragment.BranchSelectionFragment;
import ro.code4.monitorizarevot.fragment.ChatFragment;
import ro.code4.monitorizarevot.fragment.FormsListFragment;
import ro.code4.monitorizarevot.fragment.GuideFragment;
import ro.code4.monitorizarevot.net.model.LogoutListener;
import ro.code4.monitorizarevot.viewmodel.ToolbarViewModel;

public class ToolbarActivity extends BaseActivity<ToolbarViewModel> implements Navigator, HasSupportFragmentInjector {

    public static final int BRANCH_SELECTION_BACKSTACK_INDEX = 0;

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;

    @BindView(R.id.navigation_drawer)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar_menu)
    View menuButton;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String currentFragmentClassName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        ButterKnife.bind(this);

        SyncAdapter.requestSync(this);
        navigateTo(BranchSelectionFragment.newInstance());
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ToolbarViewModel.class);
    }

    @OnClick(R.id.toolbar_menu)
    void onMenuButtonClick() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @OnClick({R.id.menu_forms, R.id.menu_change_branch, R.id.menu_guide, R.id.menu_call, R.id.menu_chat, R.id.menu_logout})
    void onMenuItemClick(View view) {
        switch (view.getId()) {
            case R.id.menu_forms:
                navigateTo(FormsListFragment.newInstance());
                break;

            case R.id.menu_change_branch:
                navigateBackUntil(BRANCH_SELECTION_BACKSTACK_INDEX);
                break;

            case R.id.menu_guide:
                navigateTo(GuideFragment.newInstance());
                break;

            case R.id.menu_call:
                callSupportCenter();
                break;

            case R.id.menu_chat:
                navigateTo(ChatFragment.newInstance());
                break;

            case R.id.menu_logout:
                EventBus.getDefault().post(new LogoutListener());
                break;
        }
    }

    private void callSupportCenter() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Constants.SERVICE_CENTER_PHONE_NUMBER));
        startActivity(callIntent);
    }

    @Override
    public void navigateTo(BaseFragment fragment) {
        navigateTo(fragment, true);
    }

    @Override
    public void navigateTo(BaseFragment fragment, boolean addToBackStack) {
        String fragmentClassName = fragment.getClass().getName();
        if (currentFragmentClassName == null || !currentFragmentClassName.equals(fragmentClassName)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(fragmentClassName);
            }
            transaction.commit();
            currentFragmentClassName = fragmentClassName;
        }
        hideFocusedKeyboard();
        closeDrawer();
    }

    @Override
    public void navigateBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            onBackPressed();
        }
    }

    @Override
    public void navigateBackUntil(int backstackIndex) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while (count > backstackIndex + 1) {
            onBackPressed();
            count = getSupportFragmentManager().getBackStackEntryCount();
        }
    }

    @Override
    public void setTitle(String title) {
        toolbarTitle.setText(title);
    }

    @Override
    public void setMenu(boolean isEnabled) {
        menuButton.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        drawerLayout.setDrawerLockMode(isEnabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            hideFocusedKeyboard();
            super.onBackPressed();
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                currentFragmentClassName = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1)
                                                                      .getName();
            }
        } else {
            finish();
        }
    }

    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }
}
