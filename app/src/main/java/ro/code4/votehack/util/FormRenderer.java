package ro.code4.votehack.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import ro.code4.votehack.R;
import ro.code4.votehack.constants.QuestionType;
import ro.code4.votehack.net.model.Answer;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.widget.AnswerCheckbox;
import ro.code4.votehack.widget.AnswerCheckboxWithDetails;
import ro.code4.votehack.widget.AnswerRadioButton;
import ro.code4.votehack.widget.AnswerRadioButtonWithDetails;

public class FormRenderer {
    public static View renderQuestion(Context context, Question question) {
        switch (QuestionType.from(question.getTypeId())) {
            case SINGLE_OPTION:
                return renderSingleAnswerQuestion(context, question);
            case MULTIPLE_OPTIONS:
                return renderMultipleAnswersQuestion(context, question);
            case UNKNOWN:
            default:
                Toast.makeText(context, "Eroare in interpretarea sectiunii", Toast.LENGTH_SHORT).show();
                return new View(context);
        }
    }

    private static View renderMultipleAnswersQuestion(Context context, Question question) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (Answer answer : question.getAnswerList()) {
            if (answer.hasManualInput()) {
                AnswerCheckboxWithDetails child = new AnswerCheckboxWithDetails(context);
                child.setAnswer(answer);
                layout.addView(child, layout.getChildCount());
            } else {
                AnswerCheckbox child = new AnswerCheckbox(context);
                child.setAnswer(answer);
                layout.addView(child, layout.getChildCount());
            }
        }

        return layout;
    }

    private static View renderSingleAnswerQuestion(Context context, Question question) {
        RadioGroup group = new RadioGroup(context);
        int marginBottom = context.getResources().getDimensionPixelSize(R.dimen.question_option_margin);
        for(Answer answer : question.getAnswerList()) {
            if (answer.hasManualInput()) {
                AnswerRadioButtonWithDetails child = new AnswerRadioButtonWithDetails(context);
                setMargins(child, 0, 0, 0, marginBottom);
                child.setAnswer(answer);
                group.addView(child, group.getChildCount());
            } else {
                AnswerRadioButton child = new AnswerRadioButton(context, null, R.attr.customAnswerRadioButton);
                setMargins(child, 0, 0, 0, marginBottom);
                child.setAnswer(answer);
                group.addView(child, group.getChildCount());
            }
        }

        return group;
    }

    private static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
