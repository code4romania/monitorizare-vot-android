package ro.code4.votehack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import io.realm.Realm;
import ro.code4.votehack.fragment.BranchSelectionFragment;
import ro.code4.votehack.net.HttpCallback;
import ro.code4.votehack.net.HttpClient;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.net.model.response.VersionResponse;

public class ToolbarActivity extends BaseActivity implements Navigator {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private View menuButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        menuButton = toolbar.findViewById(R.id.toolbar_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);

        initToolbar();
        initNavigationDrawer();

        navigateTo(BranchSelectionFragment.newInstance(), false);

//        checkFormVersion();
        getForms();
    }

    private void initNavigationDrawer() {
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
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

    private void checkFormVersion() {

    }

    private void getFormVersion() {
        HttpClient.getInstance().getFormVersion(new HttpCallback<VersionResponse>(VersionResponse.class) {
            @Override
            public void onSuccess(VersionResponse response) {

            }

            @Override
            public void onError() {

            }
        });
    }

    private void getForms() {
        HttpClient.getInstance().getForm("A", new HttpCallback<Section[]>(Section[].class) {
            @Override
            public void onSuccess(Section[] sections) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(Arrays.asList(sections));
                realm.commitTransaction();
                realm.close();
            }

            @Override
            public void onError() {

            }
        });
        HttpClient.getInstance().getForm("B", new HttpCallback<Section[]>(Section[].class) {
            @Override
            public void onSuccess(Section[] sections) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(Arrays.asList(sections));
                realm.commitTransaction();
                realm.close();
            }

            @Override
            public void onError() {

            }
        });
        HttpClient.getInstance().getForm("C", new HttpCallback<Section[]>(Section[].class) {
            @Override
            public void onSuccess(Section[] sections) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(Arrays.asList(sections));
                realm.commitTransaction();
                realm.close();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initToolbar() {
        toolbar.setTitle(null);
    }

    private void callSupportCenter() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + App.SERVICE_CENTER_PHONE_NUMBER));
        startActivity(callIntent);
    }

    @Override
    public void navigateTo(BaseFragment fragment) {
        navigateTo(fragment, true);
    }

    @Override
    public void navigateTo(BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragment.getIdentifier());
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getIdentifier());
        }
        transaction.commit();
    }

    @Override
    public void navigateBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }
}
