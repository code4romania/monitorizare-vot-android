package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.adapter.QuestionsOverviewAdapter;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.model.Form;
import ro.code4.monitorizarevot.util.GridSpacingItemDecoration;
import ro.code4.monitorizarevot.util.QuestionsOverviewNavigator;
import ro.code4.monitorizarevot.viewmodel.QuestionOverviewViewModel;

public class QuestionsOverviewFragment extends BaseFragment<QuestionOverviewViewModel> implements QuestionsOverviewNavigator {

    private static final String ARG_FORM_ID = "form";

    private Form form;

    public static QuestionsOverviewFragment newInstance(String formId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FORM_ID, formId);
        QuestionsOverviewFragment fragment = new QuestionsOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.form = Data.getInstance().getForm(getArguments().getString(ARG_FORM_ID));
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_form, form.getId());
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(QuestionOverviewViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questions_overview, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.questions_overview_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new QuestionsOverviewAdapter(getActivity(), form, this));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,
                                                                     getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin),
                                                                     false));

        return rootView;
    }

    @Override
    public void showQuestionDetails(int index) {
        navigateTo(QuestionsDetailsFragment.newInstance(form.getId(), index));
    }
}
