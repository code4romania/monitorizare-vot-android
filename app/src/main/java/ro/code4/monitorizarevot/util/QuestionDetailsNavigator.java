package ro.code4.monitorizarevot.util;

import android.view.ViewGroup;

public interface QuestionDetailsNavigator {
    void onNotes();
    void onNext();
    void onSaveAnswerIfCompleted(ViewGroup questionContainer);
    void onPrevious();
}
