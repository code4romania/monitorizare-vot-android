package ro.code4.monitorizarevot.presenter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;
import ro.code4.monitorizarevot.widget.AnswerCheckbox;
import ro.code4.monitorizarevot.widget.AnswerCheckboxWithDetails;
import ro.code4.monitorizarevot.widget.AnswerRadioGroup;

public class QuestionsDetailsPresenter {

    private final Activity mActivity;

    public QuestionsDetailsPresenter(Activity activity){
        this.mActivity = activity;
    }

    public List<ResponseAnswer> getAnswerIfCompleted(ViewGroup questionContainer) {
        if(questionContainer.getChildAt(0) instanceof AnswerRadioGroup){
            return getAnswerFromRadioButton(questionContainer);
        } else if(questionContainer.getChildAt(0) instanceof LinearLayout){
            return getAnswerFromCheckbox(questionContainer);
        }
        return null;
    }

    private List<ResponseAnswer> getAnswerFromCheckbox(ViewGroup questionContainer) {
        List<ResponseAnswer> responseAnswerList = new ArrayList<>();
        LinearLayout container = (LinearLayout) questionContainer.getChildAt(0);
        int childCount = container.getChildCount();
        for(int i = 0; i< childCount; i++){
            if(container.getChildAt(i) instanceof AnswerCheckboxWithDetails){
                AnswerCheckboxWithDetails answerCheckboxWithDetails = (AnswerCheckboxWithDetails) container.getChildAt(i);
                if(answerCheckboxWithDetails.isChecked()){
                    responseAnswerList.add(answerCheckboxWithDetails.getAnswer());
                }
            } else if(container.getChildAt(i) instanceof AnswerCheckbox){
                AnswerCheckbox answerCheckbox = (AnswerCheckbox) container.getChildAt(i);
                if(answerCheckbox.isChecked()){
                    responseAnswerList.add(answerCheckbox.getAnswer());
                }
            }
        }
        return responseAnswerList;
    }

    private List<ResponseAnswer> getAnswerFromRadioButton(ViewGroup questionContainer) {
        List<ResponseAnswer> responseAnswerList = new ArrayList<>();
        AnswerRadioGroup radioButtons = (AnswerRadioGroup) questionContainer.getChildAt(0);
        if(radioButtons.getCheckedAnswer() != null){
            responseAnswerList.add(radioButtons.getCheckedAnswer());
        }
        return responseAnswerList;
    }

}
