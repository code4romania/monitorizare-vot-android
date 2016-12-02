package ro.code4.votehack.adapter;

import ro.code4.votehack.net.model.Question;

public class QuestionViewModel {
    private Question question;
    private String sectionCode;

    public QuestionViewModel(Question question, String sectionCode) {
        this.question = question;
        this.sectionCode = sectionCode;
    }

    public Question getQuestion() {
        return question;
    }

    public String getSectionCode() {
        return sectionCode;
    }
}
