package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.util.QuestionNavigator;

public class FormDetailsFragment extends BaseFragment implements QuestionNavigator {
    private Section section;
    private List<Question> questions;
    private int currentQuestion = 0;

    public static FormDetailsFragment newInstance(Section section) {
        FormDetailsFragment fragment = new FormDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("Section", section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.section = (Section) getArguments().getSerializable("Section");
        this.questions = this.section != null ? this.section.getQuestionList() : new ArrayList<Question>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        showQuestion(0);
        return rootView;
    }

    @Override
    public String getIdentifier() {
        return "FormDetailsFragment";
    }

    private void showQuestion(int index) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.details_container, QuestionFragment.newInstance(section, index))
                .commit();
        currentQuestion = index;
    }

    @Override
    public void onNotes() {
        if (currentQuestion > 0) {
            showQuestion(currentQuestion - 1);
        }
    }

    @Override
    public void onNext() {
        if (currentQuestion < questions.size() - 1) {
            showQuestion(currentQuestion + 1);
        } else {
            //TODO form is done
            navigateBack();
            Toast.makeText(getActivity(), "Formular complet", Toast.LENGTH_SHORT).show();
        }
    }
}
