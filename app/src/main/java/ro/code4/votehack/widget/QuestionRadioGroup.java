package ro.code4.votehack.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioGroup;
import android.widget.TextView;

import ro.code4.votehack.R;

public class QuestionRadioGroup extends RadioGroup {
    private TextView question;

    public QuestionRadioGroup(Context context) {
        super(context);
        init(context);
    }

    public QuestionRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_question_container, this, true);

        question = (TextView) findViewById(R.id.question_text);
    }

    public QuestionRadioGroup setQuestionText(String text) {
        this.question.setText(text);
        return this;
    }
}
