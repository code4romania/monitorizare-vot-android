package ro.code4.votehack.util;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ro.code4.votehack.adapter.QuestionViewModel;
import ro.code4.votehack.db.Data;
import ro.code4.votehack.net.model.Form;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.net.model.Section;

public class FormUtils {
    public static List<QuestionViewModel> getQuestionViewModelList(String formId) {
        Form form = Data.getInstance().getForm(formId);
        List<QuestionViewModel> questions = new ArrayList<>();
        for (Section section : form.getSections()) {
            for (Question question : section.getQuestionList()) {
                questions.add(new QuestionViewModel(question, section.getSectionCode()));
            }
        }
        return questions;
    }

    public static List<Question> getAllQuestions(String formId) {
        Form form = Data.getInstance().getForm(formId);
        List<Question> questions = new ArrayList<>();
        for (Section section : form.getSections()) {
            for (Question question : section.getQuestionList()) {
                questions.add(question);
            }
        }
        return questions;
    }
}
