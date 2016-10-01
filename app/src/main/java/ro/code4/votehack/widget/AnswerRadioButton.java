package ro.code4.votehack.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RadioButton;

import ro.code4.votehack.net.model.Answer;

public class AnswerRadioButton extends RadioButton implements AnswerLayout {
    public AnswerRadioButton(Context context) {
        super(context);
        init(context);
    }

    public AnswerRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnswerRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        //Do initializations here
    }

    @Override
    public void setAnswer(Answer answer) {
        setText(answer.getText());
    }
}
