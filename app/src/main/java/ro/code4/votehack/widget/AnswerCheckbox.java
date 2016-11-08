package ro.code4.votehack.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.CheckBox;

import ro.code4.votehack.R;
import ro.code4.votehack.net.model.Answer;

public class AnswerCheckbox extends CheckBox implements AnswerLayout {
    public AnswerCheckbox(Context context) {
        super(context);
        init(context);
    }

    public AnswerCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnswerCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerCheckbox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.question_option_text));
    }

    @Override
    public void setAnswer(Answer answer) {
        setText(answer.getText());
    }
}
