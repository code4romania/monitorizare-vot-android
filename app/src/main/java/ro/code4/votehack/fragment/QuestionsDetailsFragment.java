package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.adapter.QuestionViewModel;
import ro.code4.votehack.adapter.SyncAdapter;
import ro.code4.votehack.db.Data;
import ro.code4.votehack.net.model.Form;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.net.model.response.ResponseAnswer;
import ro.code4.votehack.presenter.QuestionsDetailsPresenter;
import ro.code4.votehack.util.FormUtils;
import ro.code4.votehack.util.QuestionDetailsNavigator;

public class QuestionsDetailsFragment extends BaseFragment implements QuestionDetailsNavigator {
    private static final String ARGS_FORM_ID = "FormId";
    private static final String ARGS_START_INDEX = "StartIndex";
    private Form form;
    private List<QuestionViewModel> questions;
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
        this.form = Data.getInstance().getForm(getArguments().getString(ARGS_FORM_ID));
        this.currentQuestion = getArguments().getInt(ARGS_START_INDEX, 0);
        this.questions = FormUtils.getQuestionViewModelList(form.getId());
        this.mPresenter = new QuestionsDetailsPresenter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        showQuestion(currentQuestion);
        return rootView;
    }

    @Override
    public String getTitle() {
        return "";
    }

    private void showQuestion(int index) {
        QuestionViewModel viewModel = questions.get(index);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.details_container, QuestionFragment.newInstance(
                        viewModel.getQuestion().getId(),
                        index,
                        questions.size(),
                        viewModel.getSectionCode()))
                .commit();
        currentQuestion = index;
    }

    @Override
    public void onNotes() {
        navigateTo(AddNoteFragment.newInstance(questions.get(currentQuestion).getQuestion().getId()));
    }

    @Override
    public void onNext() {
        if (currentQuestion < questions.size() - 1) {
            showQuestion(currentQuestion + 1);
        } else {
            //TODO form is done
            //TODO make just upload here,not the whole sync
            SyncAdapter.requestSync(getActivity());
            navigateBack();
            Toast.makeText(getActivity(), "Formular complet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveAnswerIfCompleted(ViewGroup questionContainer) {
        List<ResponseAnswer> answers = mPresenter.getAnswerIfCompleted(questionContainer);
        if (answers.size() > 0) {
            Question question = questions.get(currentQuestion).getQuestion();
            Data.getInstance().saveAnswerResponse(question, answers);
        }
    }

}
