package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.adapter.SyncAdapter;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.Answer;
import ro.code4.monitorizarevot.net.model.BranchQuestionAnswer;
import ro.code4.monitorizarevot.net.model.Question;
import ro.code4.monitorizarevot.net.model.QuestionAnswer;
import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;
import ro.code4.monitorizarevot.observable.GeneralSubscriber;
import ro.code4.monitorizarevot.presenter.QuestionsDetailsPresenter;
import ro.code4.monitorizarevot.util.FormUtils;
import ro.code4.monitorizarevot.util.NetworkUtils;
import ro.code4.monitorizarevot.util.QuestionDetailsNavigator;
import ro.code4.monitorizarevot.viewmodel.QuestionDetailsViewModel;

public class QuestionsDetailsFragment extends BaseFragment<QuestionDetailsViewModel> implements QuestionDetailsNavigator {

    private static final String ARGS_FORM_ID = "FormId";

    private static final String ARGS_START_INDEX = "StartIndex";

    private List<Question> questions;

    private int currentQuestion = -1;

    private QuestionsDetailsPresenter mPresenter;

    private Map<Question, List<ResponseAnswer>> questionsToAnswers;

    public static QuestionsDetailsFragment newInstance(String sectionCode) {
        return newInstance(sectionCode, 0);
    }

    public static QuestionsDetailsFragment newInstance(String formId, int startIndex) {
        QuestionsDetailsFragment fragment = new QuestionsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_FORM_ID, formId);
        args.putInt(ARGS_START_INDEX, startIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentQuestion = getArguments().getInt(ARGS_START_INDEX, 0);
        this.questions = FormUtils.getAllQuestions(getArguments().getString(ARGS_FORM_ID));
        this.mPresenter = new QuestionsDetailsPresenter(getActivity());
        this.questionsToAnswers = new HashMap<>();
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(QuestionDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        showQuestion(currentQuestion);
        return rootView;
    }

    private void showQuestion(int index) {
        Question question = questions.get(index);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.details_container, QuestionFragment.newInstance(
                        question.getId(),
                        index,
                        questions.size()))
                .commit();
        currentQuestion = index;
    }

    @Override
    public void onNotes() {
        navigateTo(AddNoteFragment.newInstance(questions.get(currentQuestion).getId()));
    }

    @Override
    public void onNext() {
        hideFocusedKeyboard();
        if (currentQuestion < questions.size() - 1) {
            showQuestion(currentQuestion + 1);
        } else {
            SyncAdapter.requestUploadSync(getActivity());
            navigateBack();
        }
    }

    @Override
    public void onSaveAnswerIfCompleted(ViewGroup questionContainer) {
        List<ResponseAnswer> answers = mPresenter.getAnswerIfCompleted(questionContainer);
        Question question = questions.get(currentQuestion);
        List<ResponseAnswer> oldAnswers = question.getAnswers();

        // If the answer didn't change we don't register a new response
        if(answers.containsAll(oldAnswers) && oldAnswers.containsAll(answers)) {
            return ;
        }

        questionsToAnswers.put(question, answers);
    }

    @Override
    public void onPrevious() {
        hideFocusedKeyboard();
        if (currentQuestion > 1) {
            showQuestion(currentQuestion - 1);
        } else {
            SyncAdapter.requestUploadSync(getActivity());
            navigateBack();
        }
    }

    private void syncCurrentData(BranchQuestionAnswer... branchQuestionAnswers) {
        if (!NetworkUtils.isOnline(getActivity())) {
            return ;
        }

        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        for (BranchQuestionAnswer branchQuestionAnswer : branchQuestionAnswers) {
            QuestionAnswer questionAnswer = new QuestionAnswer(branchQuestionAnswer,
                    getArguments().getString(ARGS_FORM_ID));
            questionAnswerList.add(questionAnswer);
        }

        NetworkService.syncQuestions(questionAnswerList).startRequest(new GeneralSubscriber());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        List<BranchQuestionAnswer> branchQuestionAnswerList = new ArrayList<>();
        for (Map.Entry<Question, List<ResponseAnswer>> entry : questionsToAnswers.entrySet()) {
            BranchQuestionAnswer currentBranchQuestionAnswer =
                    buildBranchQuestionAnswer(entry.getKey(), entry.getValue());
            // if there is no answer we skip this entry
            if (currentBranchQuestionAnswer == null) {
                continue;
            }

            branchQuestionAnswerList.add(currentBranchQuestionAnswer);
        }

        // Nothing to sync
        if (branchQuestionAnswerList.isEmpty()) {
            return ;
        }

        syncCurrentData(branchQuestionAnswerList
                .toArray(new BranchQuestionAnswer[branchQuestionAnswerList.size()]));
        Toast.makeText(getContext(),getString(R.string.question_confirmation_message),
                Toast.LENGTH_SHORT).show();
    }

    private BranchQuestionAnswer buildBranchQuestionAnswer(Question question,
                                                           List<ResponseAnswer> answers) {
        if (answers.isEmpty()) {
            return null;
        }

        BranchQuestionAnswer branchQuestionAnswer = new BranchQuestionAnswer(question.getId(), answers);
        Data.getInstance().saveAnswerResponse(branchQuestionAnswer);
        return branchQuestionAnswer;
    }


}
