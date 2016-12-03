package ro.code4.monitorizarevot.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.CityBranch;
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
            if (showCurrentBranch) {
                for (Question question : section.getQuestionList()) {
                    question.setRaspunsuriIntrebare(getResponseAnswersForCityBranchFromQuestion(question));
                    questions.add(question);
                }
            } else {
                questions.addAll(section.getQuestionList());
            }
        }
        return questions;
    }

    @NonNull
    private List<ResponseAnswer> getResponseAnswersForCityBranchFromQuestion(Question question) {
        return getResponseAnswerForBranch(question.getRaspunsuriIntrebare(), mCountryCode, mBranchNo);
    }

    public List<ResponseAnswer> getResponseAnswerForBranch(List<ResponseAnswer> allResponseAnswerFromQuestion,
                                            String countryCode, int branchNo) {

        List<ResponseAnswer> responseAnswerList = new ArrayList<>();
        for (ResponseAnswer responseAnswer : allResponseAnswerFromQuestion) {
            if(countryCode.equalsIgnoreCase(responseAnswer.getCodJudet())
                    && branchNo == responseAnswer.getNumarSectie()){
                responseAnswerList.add(responseAnswer);
            }
        }
        return responseAnswerList;
    }

    public Question getQuestion(int questionIndex) {
        Question question = Data.getInstance().getQuestion(questionIndex);
        question.setRaspunsuriIntrebare(getResponseAnswersForCityBranchFromQuestion(question));
        return question;
    }

    public static List<CityBranch> getCityBranchesFromQuestion(Question question) {
        List<CityBranch> cityBranches = new ArrayList<>();
        for (ResponseAnswer responseAnswer : question.getRaspunsuriIntrebare()) {
            CityBranch cityBranch = new CityBranch(responseAnswer.getCodJudet(),
                    responseAnswer.getNumarSectie());
            if(!cityBranches.contains(cityBranch)){
                cityBranches.add(cityBranch);
            }
        }
        return cityBranches;
    }
}
