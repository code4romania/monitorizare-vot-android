package ro.code4.monitorizarevot.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.Form;
import ro.code4.monitorizarevot.net.model.Question;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public class FormUtils {
    private final int mBranchNo;
    private final String mCountryCode;

    public FormUtils(){
        mCountryCode = Preferences.getCountyCode();
        mBranchNo = Preferences.getBranchNumber();
    }

    public List<Question> getAllQuestionsForCurrentBranch(String formId) {
        return getAllQuestions(formId, true);
    }

    public List<Question> getAllQuestions(String formId) {
        return getAllQuestions(formId, false);
    }

    public List<Question> getAllQuestions(String formId, boolean showCurrentBranch) {
        Form form = Data.getInstance().getForm(formId);
        List<Question> questions = new ArrayList<>();
        for (Section section : form.getSections()) {
            for (Question question : section.getQuestionList()) {
                if (showCurrentBranch) {
                    question.setRaspunsuriIntrebare(getResponseAnswersForCityBranch(question));
                }
                questions.add(question);
            }
        }
        return questions;
    }

    @NonNull
    private List<ResponseAnswer> getResponseAnswersForCityBranch(Question question) {
        List<ResponseAnswer> responseAnswerList = new ArrayList<>();
        for (ResponseAnswer responseAnswer : question.getRaspunsuriIntrebare()) {
            if(mCountryCode.equalsIgnoreCase(responseAnswer.getCodJudet())
                    && mBranchNo == responseAnswer.getNumarSectie()){
                responseAnswerList.add(responseAnswer);
            }
        }
        return responseAnswerList;
    }

    public Question getQuestion(int questionIndex) {
        Question question = Data.getInstance().getQuestion(questionIndex);
        question.setRaspunsuriIntrebare(getResponseAnswersForCityBranch(question));
        return question;
    }
}
