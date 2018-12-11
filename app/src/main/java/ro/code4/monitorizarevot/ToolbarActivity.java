package ro.code4.monitorizarevot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import ro.code4.monitorizarevot.adapter.SyncAdapter;
import ro.code4.monitorizarevot.constants.Constants;
import ro.code4.monitorizarevot.fragment.BranchSelectionFragment;
import ro.code4.monitorizarevot.fragment.FormsListFragment;
import ro.code4.monitorizarevot.fragment.GuideFragment;

public class ToolbarActivity extends BaseActivity implements Navigator {
    public static final int BRANCH_SELECTION_BACKSTACK_INDEX = 0;

    private DrawerLayout drawerLayout;
    private View menuButton;
    private TextView toolbarTitle;
    private String currentFragmentClassName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbarTitle = findViewById(R.id.toolbar_title);
        menuButton = findViewById(R.id.toolbar_menu);
        drawerLayout = findViewById(R.id.navigation_drawer);

        initNavigationDrawer();

        SyncAdapter.requestSync(this);
        navigateTo(BranchSelectionFragment.newInstance());
    }

    private void initNavigationDrawer() {
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.menu_forms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(FormsListFragment.newInstance());
            }
        });
        findViewById(R.id.menu_change_branch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackUntil(BRANCH_SELECTION_BACKSTACK_INDEX);
            }
        });
        findViewById(R.id.menu_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(GuideFragment.newInstance());
            }
        });
        findViewById(R.id.menu_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSupportCenter();
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
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            hideFocusedKeyboard();
            super.onBackPressed();
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                currentFragmentClassName = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            }
        } else {
            finish();
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

    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
