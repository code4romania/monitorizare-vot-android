package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.db.Data;
import ro.code4.votehack.net.model.Section;

public class FormsListFragment extends BaseFragment implements View.OnClickListener {
    public static FormsListFragment newInstance() {
        return new FormsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forms_list, container, false);

        rootView.findViewById(R.id.button_form_1).setOnClickListener(this);
        rootView.findViewById(R.id.button_form_2).setOnClickListener(this);
        rootView.findViewById(R.id.button_form_3).setOnClickListener(this);
        rootView.findViewById(R.id.button_form_notes).setOnClickListener(this);

        ((TextView) rootView.findViewById(R.id.votingBranch)).setText("GR 1123"); //set selected voting branch here

        return rootView;
    }

    @Override
    public String getIdentifier() {
        return "FormsListFragment";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_form_1:
                showSection(Data.getInstance().getSectionA());
                break;
            case R.id.button_form_2:
                showSection(Data.getInstance().getSectionB());
                break;
            case R.id.button_form_3:
                showSection(Data.getInstance().getSectionC());
                break;
            case R.id.button_form_notes:
                break;
        }
    }

    private void showSection(Section section) {
        if (section != null && section.getQuestionList() != null && section.getQuestionList().size() > 0) {
            navigateTo(QuestionsOverviewFragment.newInstance(section.getSectionCode()));
        } else {
            Toast.makeText(getActivity(), getString(R.string.error_no_form_data), Toast.LENGTH_SHORT).show();
        }
    }
}
