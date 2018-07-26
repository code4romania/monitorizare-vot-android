package ro.code4.monitorizarevot.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.net.model.Answer;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class AnswerCheckboxWithDetails extends LinearLayout implements AnswerLayout, CompoundButton.OnCheckedChangeListener, Checkable {
    private AnswerCheckbox checkbox;
    private EditText details;

    public AnswerCheckboxWithDetails(Context context) {
        super(context);
        init(context);
    }

    public AnswerCheckboxWithDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnswerCheckboxWithDetails(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerCheckboxWithDetails(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_answer_checkbox_details, this, true);

        setOrientation(VERTICAL);

        checkbox = findViewById(R.id.answer_checkbox);
        details = findViewById(R.id.answer_details);

        checkbox.setOnCheckedChangeListener(this);
        updateDetailsVisibility(isChecked());
    }

    @Override
    public void setAnswer(Answer answer) {
        checkbox.setAnswer(answer);
    }

    @Override
    public void setDetail(String detail) {
        details.setText(detail);
    }

    @Override
    public ResponseAnswer getAnswer() {
        return new ResponseAnswer(checkbox.getAnswer().getOptionId(),
                details.getText().toString());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        updateDetailsVisibility(isChecked);
    }

    private void updateDetailsVisibility(boolean isChecked) {
        details.setVisibility(isChecked ? VISIBLE : GONE);
    }

    @Override
    public void setChecked(boolean b) {
        checkbox.setChecked(b);
    }

    @Override
    public boolean isChecked() {
        return checkbox.isChecked();
    }

    @Override
    public void toggle() {
        checkbox.toggle();
    }
}
