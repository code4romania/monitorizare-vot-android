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
import ro.code4.votehack.db.Data;
import ro.code4.votehack.net.HttpCallback;
import ro.code4.votehack.net.HttpClient;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.net.model.Form;
import ro.code4.votehack.net.model.response.ResponseAnswer;
import ro.code4.votehack.net.model.response.VersionResponse;
import ro.code4.votehack.presenter.QuestionsDetailsPresenter;
import ro.code4.votehack.util.QuestionDetailsNavigator;

public class QuestionsDetailsFragment extends BaseFragment implements QuestionDetailsNavigator {
    private static final String ARGS_SECTION_CODE = "SectionCode";
    private static final String ARGS_START_INDEX = "StartIndex";
    private Form form;
    private List<Question> questions;
    private int currentQuestion = 0;
    private int startIndex;

    private QuestionsDetailsPresenter mPresenter;

    public static QuestionsDetailsFragment newInstance(String sectionCode) {
        return newInstance(sectionCode, 0);
    }

    public static QuestionsDetailsFragment newInstance(String sectionCode, int startIndex) {
        QuestionsDetailsFragment fragment = new QuestionsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_SECTION_CODE, sectionCode);
        args.putInt(ARGS_START_INDEX, startIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.form = Data.getInstance().getForm(getArguments().getString(ARGS_SECTION_CODE));
        this.startIndex = getArguments().getInt(ARGS_START_INDEX, 0);
        this.questions = this.form != null ? this.form.getQuestionList() : new ArrayList<Question>();
        this.mPresenter = new QuestionsDetailsPresenter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        showQuestion(startIndex);
        return rootView;
    }

    @Override
    public String getTitle() {
        return "";
    }

    private void showQuestion(int index) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.details_container, QuestionFragment.newInstance(form, index))
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

    @Override
    public void onSaveAnswerIfCompleted(ViewGroup questionContainer) {
        List<ResponseAnswer> answers = mPresenter.getAnswerIfCompleted(questionContainer);
        if (answers.size() > 0) {
            Question question = questions.get(currentQuestion);
            Data.getInstance().saveAnswerResponse(question, answers);
        }
    }

    public void postQuestionResponse(){
        HttpClient.getInstance().postQuestionAnswer(new HttpCallback<VersionResponse>(VersionResponse.class) {
            @Override
            public void onSuccess(VersionResponse response) {

            }

            @Override
            public void onError() {

            }
        }, null);
    }
}
