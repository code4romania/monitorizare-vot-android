package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;

public class BranchDetailsFragment extends BaseFragment {
    private RadioGroup environmentRadioGroup, sexRadioGroup;

    public static BranchDetailsFragment newInstance() {
        return new BranchDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branch_details, container, false);

        environmentRadioGroup = (RadioGroup) rootView.findViewById(R.id.branch_group_environment);
        sexRadioGroup = (RadioGroup) rootView.findViewById(R.id.branch_group_sex);

        setContinueButton((Button) rootView.findViewById(R.id.button_continue));

        return rootView;
    }

    private void setContinueButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (environmentRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_environment, Toast.LENGTH_SHORT).show();
                } else if (sexRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_sex, Toast.LENGTH_SHORT).show();
                } else {
                    navigateTo(FormsListFragment.newInstance());
                }
            }
        });
    }

    @Override
    public String getIdentifier() {
        return "BranchDetailsFragment";
    }
}
