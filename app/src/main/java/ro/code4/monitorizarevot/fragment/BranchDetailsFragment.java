package ro.code4.monitorizarevot.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.db.Preferences;

public class BranchDetailsFragment extends BaseFragment implements View.OnClickListener {
    private RadioGroup environmentRadioGroup, sexRadioGroup;
    private TextView timeEnterText, timeLeaveText;
    private Calendar timeEnter, timeLeave; //TODO persist these values

    public static BranchDetailsFragment newInstance() {
        return new BranchDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branch_details, container, false);

        environmentRadioGroup = (RadioGroup) rootView.findViewById(R.id.branch_group_environment);
        sexRadioGroup = (RadioGroup) rootView.findViewById(R.id.branch_group_sex);
        timeEnterText = (TextView) rootView.findViewById(R.id.branch_time_enter);
        timeLeaveText = (TextView) rootView.findViewById(R.id.branch_time_leave);

        timeEnterText.setOnClickListener(this);
        timeLeaveText.setOnClickListener(this);

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

    private void showTimePicker(int titleId, TimePickerDialog.OnTimeSetListener listener) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                listener, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle(titleId);
        timePickerDialog.show();
    }

    private void updateCalendar(Calendar calendar, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
    }

    private void updateTimeText(Calendar calendar, TextView text) {
        text.setText(new SimpleDateFormat("HH:mm", Locale.US).format(calendar.getTime()));
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_branch_details);
    }
}
