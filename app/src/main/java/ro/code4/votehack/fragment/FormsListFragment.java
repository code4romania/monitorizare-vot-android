package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.net.model.Section;

public class FormsListFragment extends BaseFragment implements View.OnClickListener {
    public static FormsListFragment newInstance() {
        return new FormsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        rootView.findViewById(R.id.button_form_1).setOnClickListener(this);
        rootView.findViewById(R.id.button_form_2).setOnClickListener(this);
        rootView.findViewById(R.id.button_form_3).setOnClickListener(this);
        rootView.findViewById(R.id.button_form_notes).setOnClickListener(this);

        return rootView;
    }

    @Override
    public String getIdentifier() {
        return "FormsListFragment";
    }

    @Override
    public void onClick(View v) {
        String dummyJson = getString(R.string.dummy_form_json);
        Section[] sections = new Gson().fromJson(dummyJson, Section[].class);

        switch (v.getId()) {
            case R.id.button_form_1:
                navigateTo(FormDetailsFragment.newInstance(sections[0]));
                break;
            case R.id.button_form_2:
                navigateTo(FormDetailsFragment.newInstance(sections[1]));
                break;
            case R.id.button_form_3:
                navigateTo(FormDetailsFragment.newInstance(sections[1]));
                break;
            case R.id.button_form_notes:
                break;
        }
    }
}
