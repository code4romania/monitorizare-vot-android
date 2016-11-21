package ro.code4.votehack.util;

import android.view.ViewGroup;

public interface QuestionDetailsNavigator {
    void onNotes();
    void onNext();
    void onSaveAnswerIfCompleted(ViewGroup questionContainer);
}
