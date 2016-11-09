package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.adapter.QuestionsOverviewAdapter;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.util.QuestionsOverviewNavigator;

public class QuestionsOverviewFragment extends BaseFragment implements QuestionsOverviewNavigator {
    private static final String ARG_SECTION = "section";
    private Section section;

    public static QuestionsOverviewFragment newInstance(Section section) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION, section);
        QuestionsOverviewFragment fragment = new QuestionsOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.section = (Section) getArguments().getSerializable(ARG_SECTION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questions_overview, container, false);

        ((TextView) rootView.findViewById(R.id.questions_overview_description)).setText(section.getDescription());

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.questions_overview_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new QuestionsOverviewAdapter(section, this));

        return rootView;
    }

    @Override
    public String getIdentifier() {
        return "QuestionsOverviewFragment";
    }

    @Override
    public void showQuestionDetails(int index) {
        navigateTo(QuestionsDetailsFragment.newInstance(section, index));
    }
}
