package ro.code4.votehack.widget;

import ro.code4.votehack.net.model.Answer;
import ro.code4.votehack.net.model.response.ResponseAnswer;

public interface AnswerLayout {
    void setAnswer(Answer answer);
    void setDetail(String detail);
    ResponseAnswer getAnswer();
}
