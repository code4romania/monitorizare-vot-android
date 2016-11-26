package ro.code4.votehack.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import ro.code4.votehack.R;
import ro.code4.votehack.net.model.Answer;
import ro.code4.votehack.net.model.response.ResponseAnswer;

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
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.button_height)
        ));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.question_option_text));
    }

    @Override
    public void setAnswer(Answer answer) {
        setTag(new ResponseAnswer(answer.getId()));
        setText(answer.getText());
    }

    @Override
    public void setDetail(String detail) {

    }

    @Override
    public ResponseAnswer getAnswer() {
        return (ResponseAnswer) getTag();
    }
}
