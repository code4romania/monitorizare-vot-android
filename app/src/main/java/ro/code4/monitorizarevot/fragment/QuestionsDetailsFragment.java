package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.adapter.SyncAdapter;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.NetworkService;
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
        if (answers.size() > 0) {
            BranchQuestionAnswer branchQuestionAnswer = new BranchQuestionAnswer(question.getId(), answers);
            Data.getInstance().saveAnswerResponse(branchQuestionAnswer);
            syncCurrentData(branchQuestionAnswer);
            Toast.makeText(getContext(),getString(R.string.question_confirmation_message),Toast.LENGTH_SHORT).show();
        }
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

    private void syncCurrentData(BranchQuestionAnswer branchQuestionAnswer) {
        if (NetworkUtils.isOnline(getActivity())) {
            QuestionAnswer questionAnswer = new QuestionAnswer(branchQuestionAnswer,
                                                               getArguments().getString(ARGS_FORM_ID));
            NetworkService.syncCurrentQuestion(questionAnswer).startRequest(new GeneralSubscriber());
        }
    }
}
