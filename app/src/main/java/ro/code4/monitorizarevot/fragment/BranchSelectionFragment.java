package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.constants.County;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.viewmodel.BranchSelectionViewModel;

public class BranchSelectionFragment extends BaseFragment<BranchSelectionViewModel> {

    private Spinner countySpinner;

    private EditText branchNumber;

    private County selectedCounty;

    public static BranchSelectionFragment newInstance() {
        return new BranchSelectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branch_selection, container, false);

        countySpinner = rootView.findViewById(R.id.branch_selector_county);
        branchNumber = rootView.findViewById(R.id.branch_number_input);

        branchNumber.setEnabled(false);

        setCountiesDropdown(countySpinner);
        setContinueButton(rootView.findViewById(R.id.button_continue));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Preferences.hasBranch()) {
            countySpinner.setSelection(County.getIndexByCountyCode(Preferences.getCountyCode()));
            branchNumber.setText(String.valueOf(Preferences.getBranchNumber()));
            branchNumber.setEnabled(true);
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_branch_selection);
    }

    @Override
    public boolean withMenu() {
        return false;
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(BranchSelectionViewModel.class);
    }

    private void setCountiesDropdown(Spinner dropdown) {
        ArrayAdapter<String> countyAdapter = new ArrayAdapter<>(getActivity(),
                                                                R.layout.support_simple_spinner_dropdown_item, County.getCountiesNames());
        dropdown.setAdapter(countyAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCounty = County.getCountyByIndex(position);
                branchNumber.setEnabled(true);
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
                } else if (branchNumber.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_number, Toast.LENGTH_SHORT).show();
                } else if (getBranchNumber() <= 0) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_number_minus, Toast.LENGTH_SHORT).show();
                } else if (getBranchNumber() > selectedCounty.getBranchesCount()) {
                    Toast.makeText(getActivity(), getBranchExceededError(), Toast.LENGTH_SHORT).show();
                } else {
                    persistSelection();
                    navigateTo(BranchDetailsFragment.newInstance());
                }
            }
        });
    }

    private void persistSelection() {
        Preferences.saveCountyCode(County.getCountyByIndex(countySpinner.getSelectedItemPosition()).getCode());
        Preferences.saveBranchNumber(getBranchNumber());
    }

    public int getBranchNumber() {
        try {
            return Integer.parseInt(branchNumber.getText().toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getBranchExceededError() {
        return getString(R.string.invalid_branch_number_max,
                         selectedCounty.getName(), String.valueOf(selectedCounty.getBranchesCount()));
    }
}
