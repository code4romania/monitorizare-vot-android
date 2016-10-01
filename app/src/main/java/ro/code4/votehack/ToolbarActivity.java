package ro.code4.votehack;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import ro.code4.votehack.fragment.FormsListFragment;

public class ToolbarActivity extends BaseActivity implements Navigator {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        initToolbar();

        navigateTo(FormsListFragment.newInstance(), false);
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
}
