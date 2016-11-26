package ro.code4.votehack.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import ro.code4.votehack.R;
import ro.code4.votehack.net.model.Answer;
import ro.code4.votehack.net.model.response.ResponseAnswer;

public class AnswerRadioButtonWithDetails extends LinearLayout implements AnswerLayout, CompoundButton.OnCheckedChangeListener, Checkable {
    private AnswerRadioButton radioButton;
    private EditText details;

    public AnswerRadioButtonWithDetails(Context context) {
        super(context);
        init(context);
    }

    public AnswerRadioButtonWithDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnswerRadioButtonWithDetails(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerRadioButtonWithDetails(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_answer_radio_details, this, true);

        setOrientation(VERTICAL);
        setLayoutParams(new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        radioButton = (AnswerRadioButton) findViewById(R.id.answer_radio);
        details = (EditText) findViewById(R.id.answer_details);

        radioButton.setOnCheckedChangeListener(this);
        updateDetailsVisibility(isChecked());
    }

    @Override
    public void setAnswer(Answer answer) {
        radioButton.setAnswer(answer);
    }

    @Override
    public ResponseAnswer getAnswer() {
        return new ResponseAnswer(radioButton.getAnswer().getIdOptiune(),
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
        radioButton.setChecked(b);
    }

    @Override
    public boolean isChecked() {
        return radioButton.isChecked();
    }

    @Override
    public void toggle() {
        radioButton.toggle();
    }
}
