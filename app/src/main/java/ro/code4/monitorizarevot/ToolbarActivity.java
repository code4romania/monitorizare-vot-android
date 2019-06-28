package ro.code4.monitorizarevot;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ro.code4.monitorizarevot.adapter.SyncAdapter;
import ro.code4.monitorizarevot.constants.Constants;
import ro.code4.monitorizarevot.fragment.BranchSelectionFragment;
import ro.code4.monitorizarevot.fragment.FormsListFragment;
import ro.code4.monitorizarevot.fragment.GuideFragment;
import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.LogoutListener;
import ro.code4.monitorizarevot.observable.ObservableListener;
import ro.code4.monitorizarevot.viewmodel.ToolbarViewModel;

public class ToolbarActivity extends BaseActivity<ToolbarViewModel> implements Navigator, HasSupportFragmentInjector {

    public static final int BRANCH_SELECTION_BACKSTACK_INDEX = 0;

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;

    private DrawerLayout drawerLayout;

    private View menuButton;

    private TextView toolbarTitle;

    private String currentFragmentClassName;

    private TextView previousSelectedItem;
    private TextView formsMenuItem;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbarTitle = findViewById(R.id.toolbar_title);
        menuButton = findViewById(R.id.toolbar_menu);
        drawerLayout = findViewById(R.id.navigation_drawer);
        formsMenuItem = findViewById(R.id.menu_forms);
        progressBar = findViewById(R.id.progress_circular);

        initNavigationDrawer();

        requestSync(false);
        launchWithSyncedCounties();
    }

    private void launchWithSyncedCounties() {
        progressBar.setVisibility(View.VISIBLE);
        NetworkService.doGetCounties().startRequest(new ObservableListener<Boolean>() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                navigateTo(BranchSelectionFragment.newInstance());
            }
        });
    }

    public void requestSync(final boolean shouldUpdateFormsFragment) {
        SyncAdapter.requestSync(this, new SyncDataCallback() {
            @Override
            public void onSyncedForms() {
                // notify FormsListFragment if sync request came from it
                if (shouldUpdateFormsFragment) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

                    if (fragment != null && fragment instanceof FormsListFragment) {
                        ((FormsListFragment) fragment).onSyncedForms();
                    }
                }
            }
        });
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ToolbarViewModel.class);
    }

    private void setSelectedMenuItem(View v) {
        if(previousSelectedItem != null) {
            previousSelectedItem.setTextColor(ContextCompat.getColor(ToolbarActivity.this,R.color.textPrimary));
            previousSelectedItem.setBackground(ContextCompat.getDrawable(ToolbarActivity.this,R.drawable.background_menu_item));
        }
        previousSelectedItem = (TextView) v;
        previousSelectedItem.setTextColor(ContextCompat.getColor(ToolbarActivity.this,R.color.primaryDark));
        previousSelectedItem.setBackgroundColor(ContextCompat.getColor(ToolbarActivity.this,R.color.grey));
    }
    private void initNavigationDrawer() {
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        setSelectedMenuItem(formsMenuItem);
        formsMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedMenuItem(v);
                navigateTo(FormsListFragment.newInstance());
            }
        });
        findViewById(R.id.menu_change_branch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // In Branch Selection Fragment we don't have a menu and this Fragment leads the user to Forms page so we set it directly to Forms
                setSelectedMenuItem(formsMenuItem);
                navigateBackUntil(BRANCH_SELECTION_BACKSTACK_INDEX);
            }
        });
        findViewById(R.id.menu_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedMenuItem(v);
                navigateTo(GuideFragment.newInstance());
            }
        });
        findViewById(R.id.menu_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSupportCenter();
            }
        });
        findViewById(R.id.menu_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new LogoutListener());
            }
        });

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
        if (currentFragmentClassName == null ||
                !currentFragmentClassName.equals(fragmentClassName)) {
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
        menuButton.setVisibility(isEnabled ?
                                 View.VISIBLE :
                                 View.GONE);
        drawerLayout.setDrawerLockMode(isEnabled ?
                                       DrawerLayout.LOCK_MODE_UNLOCKED :
                                       DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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

    public interface SyncDataCallback {
        void onSyncedForms();
    }
}
