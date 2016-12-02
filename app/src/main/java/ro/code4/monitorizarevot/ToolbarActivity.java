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
import android.widget.Toast;

import ro.code4.monitorizarevot.adapter.SyncAdapter;
import ro.code4.monitorizarevot.constants.Constants;
import ro.code4.monitorizarevot.fragment.BranchSelectionFragment;

public class ToolbarActivity extends BaseActivity implements Navigator {
    private DrawerLayout drawerLayout;
    private View menuButton;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        menuButton = findViewById(R.id.toolbar_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);

        initNavigationDrawer();

        SyncAdapter.requestSync(this);
        navigateTo(BranchSelectionFragment.newInstance(), false);
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
                Toast.makeText(ToolbarActivity.this, "Forms coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.menu_questions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolbarActivity.this, "Questions coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.menu_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolbarActivity.this, "Guide coming soon", Toast.LENGTH_SHORT).show();
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }

    @Override
    public void navigateBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void setTitle(String title) {
        toolbarTitle.setText(title);
    }
}
