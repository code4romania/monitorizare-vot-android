package ro.code4.votehack;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import java.util.Arrays;

import io.realm.Realm;
import ro.code4.votehack.fragment.FormsListFragment;
import ro.code4.votehack.net.HttpCallback;
import ro.code4.votehack.net.HttpClient;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.net.model.response.VersionResponse;

public class ToolbarActivity extends BaseActivity implements Navigator {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        initToolbar();

        navigateTo(FormsListFragment.newInstance(), false);

//        checkFormVersion();
        getForms();
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

    @Override
    public void navigateTo(BaseFragment fragment) {
        navigateTo(fragment, true);
    }

    @Override
    public void navigateTo(BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragment.getIdentifier());
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getIdentifier());
        }
        transaction.commit();
    }

    @Override
    public void navigateBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }
}
