package ro.code4.monitorizarevot.util;

import java.util.ArrayList;
import java.util.List;

import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.model.Form;
import ro.code4.monitorizarevot.net.model.Question;
import ro.code4.monitorizarevot.net.model.Section;

public class FormUtils {
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
