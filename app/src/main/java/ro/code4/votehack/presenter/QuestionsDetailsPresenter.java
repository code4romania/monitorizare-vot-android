package ro.code4.votehack.presenter;


import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import ro.code4.votehack.net.model.response.ResponseAnswer;
import ro.code4.votehack.widget.AnswerCheckbox;
import ro.code4.votehack.widget.AnswerCheckboxWithDetails;
import ro.code4.votehack.widget.AnswerRadioButton;
import ro.code4.votehack.widget.AnswerRadioButtonWithDetails;

public class QuestionsDetailsPresenter {

    private final Activity mActivity;

    public QuestionsDetailsPresenter(Activity activity){
        this.mActivity = activity;
    }

    public ResponseAnswer getAnswerIfCompleted(ViewGroup questionContainer) {
        if(questionContainer.getChildAt(0) instanceof RadioGroup){
            return getAnswerFromRadioButton(questionContainer);
        } else if(questionContainer.getChildAt(0) instanceof LinearLayout){
            return getAnswerFromCheckbox(questionContainer);
        }
        return null;
    }

    private ResponseAnswer getAnswerFromCheckbox(ViewGroup questionContainer) {
        LinearLayout container = (LinearLayout) questionContainer.getChildAt(0);
        int childCount = container.getChildCount();
        for(int i = 0; i< childCount; i++){
            if(container.getChildAt(i) instanceof AnswerCheckboxWithDetails){
                AnswerCheckboxWithDetails answerCheckboxWithDetails = (AnswerCheckboxWithDetails) container.getChildAt(i);
                if(answerCheckboxWithDetails.isChecked()){
                    return answerCheckboxWithDetails.getAnswer();
                }
            } else if(container.getChildAt(i) instanceof AnswerCheckbox){
                AnswerCheckbox answerCheckbox = (AnswerCheckbox) container.getChildAt(i);
                if(answerCheckbox.isChecked()){
                    return answerCheckbox.getAnswer();
                }
            }
        }
        return null;
    }

    private ResponseAnswer getAnswerFromRadioButton(ViewGroup questionContainer) {
        RadioGroup radioButtons = (RadioGroup) questionContainer.getChildAt(0);
        int index = radioButtons.indexOfChild(mActivity.findViewById(radioButtons.getCheckedRadioButtonId()));
        if(index >= 0){
            if(radioButtons.getChildAt(index) instanceof AnswerRadioButton){
                return ((AnswerRadioButton)(radioButtons.getChildAt(index))).getAnswer();
            } else if(radioButtons.getChildAt(index) instanceof AnswerRadioButtonWithDetails){
                return ((AnswerRadioButtonWithDetails)(radioButtons.getChildAt(index))).getAnswer();
            }
        }
        return null;
    }

}
