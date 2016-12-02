package ro.code4.monitorizarevot.widget;

import ro.code4.monitorizarevot.net.model.Answer;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;

public interface AnswerLayout {
    void setAnswer(Answer answer);
    void setDetail(String detail);
    ResponseAnswer getAnswer();
}
