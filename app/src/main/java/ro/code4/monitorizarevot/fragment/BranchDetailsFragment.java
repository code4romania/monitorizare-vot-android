package ro.code4.monitorizarevot.fragment;

import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.constants.Constants;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.util.DateUtils;
import ro.code4.monitorizarevot.viewmodel.BranchDetailsViewModel;
import ro.code4.monitorizarevot.widget.ChangeBranchBarLayout;

import static ro.code4.monitorizarevot.ToolbarActivity.BRANCH_SELECTION_BACKSTACK_INDEX;

public class BranchDetailsFragment extends BaseFragment<BranchDetailsViewModel> implements View.OnClickListener {

    private RadioGroup environmentRadioGroup, sexRadioGroup;

    private RadioButton urban, rural, male, female;

    private TextView timeEnterText, timeLeaveText;

    private Calendar timeEnter, timeLeave;

    private BranchDetails existingBranchDetails;

    public static BranchDetailsFragment newInstance() {
        return new BranchDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branch_details, container, false);

        environmentRadioGroup = rootView.findViewById(R.id.branch_group_environment);
        urban = rootView.findViewById(R.id.branch_environment_urban);
        rural = rootView.findViewById(R.id.branch_environment_rural);
        male = rootView.findViewById(R.id.branch_sex_male);
        female = rootView.findViewById(R.id.branch_sex_female);
        sexRadioGroup = rootView.findViewById(R.id.branch_group_sex);
        timeEnterText = rootView.findViewById(R.id.branch_time_enter);
        timeLeaveText = rootView.findViewById(R.id.branch_time_leave);

        timeEnterText.setOnClickListener(this);
        timeLeaveText.setOnClickListener(this);

        setBranchBar((ChangeBranchBarLayout) rootView.findViewById(R.id.change_branch_bar));
        setContinueButton((Button) rootView.findViewById(R.id.button_continue));

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        existingBranchDetails = Data.getInstance().getCurrentBranchDetails();
        if (existingBranchDetails != null) {
            updateExistingDetails(existingBranchDetails);
        }
    }

    private void updateExistingDetails(BranchDetails currentBranchDetails) {
        urban.setChecked(currentBranchDetails.isUrban());
        rural.setChecked(!currentBranchDetails.isUrban());
        male.setChecked(!currentBranchDetails.isFemale());
        female.setChecked(currentBranchDetails.isFemale());
        timeEnter = DateUtils.stringToCalendar(currentBranchDetails.getTimeEnter());
        if (timeEnter != null) {
            updateTimeText(timeEnter, timeEnterText);
        }
        timeLeave = DateUtils.stringToCalendar(currentBranchDetails.getTimeLeave());
        if (timeLeave != null) {
            updateTimeText(timeLeave, timeLeaveText);
        }
    }

    private void setBranchBar(ChangeBranchBarLayout barLayout) {
        barLayout.setBranchText(Preferences.getCountyCode() + " " + Preferences.getBranchNumber());
        barLayout.setChangeBranchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackUntil(BRANCH_SELECTION_BACKSTACK_INDEX);
            }
        });
    }

    private void setContinueButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (environmentRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_environment, Toast.LENGTH_SHORT).show();
                } else if (sexRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_sex, Toast.LENGTH_SHORT).show();
                } else if (timeEnter == null) {
                    Toast.makeText(getActivity(), R.string.invalid_branch_time_in, Toast.LENGTH_SHORT).show();
                } else {
                    persistSelection();
                    navigateTo(FormsListFragment.newInstance());
                }
            }
        });
    }

    private void showTimePicker(int titleId, TimePickerDialog.OnTimeSetListener listener) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                                                 listener, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle(titleId);
        timePickerDialog.show();
    }    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.branch_time_enter:
                showTimePicker(R.string.branch_choose_time_enter, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeEnter = Calendar.getInstance();
                        updateCalendar(timeEnter, hourOfDay, minute);
                        updateTimeText(timeEnter, timeEnterText);
                    }
                });
                break;
            case R.id.branch_time_leave:
                showTimePicker(R.string.branch_choose_time_leave, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeLeave = Calendar.getInstance();
                        updateCalendar(timeLeave, hourOfDay, minute);
                        updateTimeText(timeLeave, timeLeaveText);
                    }
                });
                break;
        }
    }

    private void updateCalendar(Calendar calendar, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
    }

    private void updateTimeText(Calendar calendar, TextView text) {
        text.setText(new SimpleDateFormat(Constants.TIME_FORMAT, Locale.US).format(calendar.getTime()));
    }

    private void persistSelection() {
        BranchDetails currentBranchDetails = new BranchDetails(
                Preferences.getCountyCode(),
                Preferences.getBranchNumber(),
                environmentRadioGroup.getCheckedRadioButtonId() == R.id.branch_environment_urban,
                sexRadioGroup.getCheckedRadioButtonId() == R.id.branch_sex_female,
                DateUtils.calendarToString(timeEnter),
                DateUtils.calendarToString(timeLeave));
        if (existingBranchDetails == null || !existingBranchDetails.equals(currentBranchDetails)) {
            Data.getInstance().saveBranchDetails(currentBranchDetails);
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_branch_details);
    }

    @Override
    public boolean withMenu() {
        return false;
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(BranchDetailsViewModel.class);
    }


}
