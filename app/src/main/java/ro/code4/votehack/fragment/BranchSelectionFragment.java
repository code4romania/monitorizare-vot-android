package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.constants.County;

public class BranchSelectionFragment extends BaseFragment {
    private Spinner branchNumberSpinner;
    private County selectedCounty;
    private Integer selectedNumber;

    public static BranchSelectionFragment newInstance() {
        return new BranchSelectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branch_selection, container, false);

        branchNumberSpinner = (Spinner) rootView.findViewById(R.id.branch_selector_number);

        setCountiesDropdown((Spinner) rootView.findViewById(R.id.branch_selector_county));
        setContinueButton(rootView.findViewById(R.id.button_continue));

        return rootView;
    }

    private void setCountiesDropdown(Spinner dropdown) {
        ArrayAdapter<String> countyAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, County.getCountiesNames());
        dropdown.setAdapter(countyAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCounty = County.getCountyByIndex(position);
                selectedNumber = null;
                setNumberDropdown(branchNumberSpinner, County.getCountyBranches(selectedCounty));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setNumberDropdown(Spinner dropdown, List<String> values) {
        ArrayAdapter<String> countyAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, values);
        dropdown.setAdapter(countyAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNumber = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setContinueButton(View button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCounty == null) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_county, Toast.LENGTH_SHORT).show();
                } else if (selectedNumber == null) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_number, Toast.LENGTH_SHORT).show();
                } else {
                    navigateTo(BranchDetailsFragment.newInstance());
                }
            }
        });
    }

    @Override
    public String getIdentifier() {
        return "BranchSelectionFragment";
    }
}
