package ro.code4.votehack.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import ro.code4.votehack.R;

public class QuestionCheckboxLayout extends LinearLayout {
    private TextView question;

    public QuestionCheckboxLayout(Context context) {
        super(context);
        init(context);
    }

    public QuestionCheckboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionCheckboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public QuestionCheckboxLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_question_container, this, true);

        setOrientation(VERTICAL);

        question = (TextView) findViewById(R.id.question_text);
    }

    public QuestionCheckboxLayout setQuestionText(String text) {
        this.question.setText(text);
        return this;
    }
}
