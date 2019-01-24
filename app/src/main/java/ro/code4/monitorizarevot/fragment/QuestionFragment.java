package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.net.model.Question;
import ro.code4.monitorizarevot.util.FormRenderer;
import ro.code4.monitorizarevot.util.FormUtils;
import ro.code4.monitorizarevot.util.QuestionDetailsNavigator;
import ro.code4.monitorizarevot.viewmodel.QuestionViewModel;

public class QuestionFragment extends BaseFragment<QuestionViewModel> {

    private static final String ARG_QUESTION_ID = "questionId";

    private static final String ARG_SIZE = "numberOfQuestions";

    private static final String ARG_INDEX = "indexOfQuestion";

    private Question question;

    private QuestionDetailsNavigator navigator;

    private Button nextButton;

    private Button previousButton;

    private int numberOfQuestions;

    private int questionIndex;

    public static QuestionFragment newInstance(int questionId, int index, int numberOfQuestions) {
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_ID, questionId);
        args.putInt(ARG_INDEX, index + 1);
        args.putInt(ARG_SIZE, numberOfQuestions);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() == null || !(getParentFragment() instanceof QuestionDetailsNavigator)) {
            throw new RuntimeException("Fragment must be a child of a fragment implementing QuestionDetailsNavigator");
        } else {
            navigator = (QuestionDetailsNavigator) getParentFragment();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.question = FormUtils.getQuestion(getArguments().getInt(ARG_QUESTION_ID));
        this.questionIndex = getArguments().getInt(ARG_INDEX);
        this.numberOfQuestions = getArguments().getInt(ARG_SIZE);
    }

    @Override
    public String getTitle() {
        return question.getCode();
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(QuestionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        setDescription((TextView) rootView.findViewById(R.id.question_description));

        if (questionIndex == numberOfQuestions) {
            ((TextView) rootView.findViewById(R.id.button_question_next)).setText(R.string.question_finish);
        }

        final ViewGroup questionContainer = rootView.findViewById(R.id.question_container);
        questionContainer.addView(FormRenderer.renderQuestion(getActivity(), question));
        rootView.findViewById(R.id.button_question_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        rootView.findViewById(R.id.button_question_notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.onSaveAnswerIfCompleted(questionContainer);
                navigator.onNotes();
            }
        });
        nextButton = rootView.findViewById(R.id.button_question_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.onSaveAnswerIfCompleted(questionContainer);
                navigator.onNext();

            }
        });

        previousButton = rootView.findViewById(R.id.button_question_previous);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.onSaveAnswerIfCompleted(questionContainer);
                navigator.onPrevious();
                hideButtons();
            }
        });
        hideButtons();
        return rootView;
    }

    private void setDescription(TextView description) {
        description.setText(question.getText());
    }

    private void hideButtons(){
        if(questionIndex > 1){
            previousButton.setVisibility(View.VISIBLE);
        }else{
            previousButton.setVisibility(View.GONE);
        }
    }
}
