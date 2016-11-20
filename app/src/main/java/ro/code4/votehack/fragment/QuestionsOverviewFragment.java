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
import ro.code4.votehack.db.Data;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.util.QuestionsOverviewNavigator;

public class QuestionsOverviewFragment extends BaseFragment implements QuestionsOverviewNavigator {
    private static final String ARG_SECTION_CODE = "section";
    private Section section;

    public static QuestionsOverviewFragment newInstance(String sectionCode) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_CODE, sectionCode);
        QuestionsOverviewFragment fragment = new QuestionsOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.section = Data.getInstance().getSection(getArguments().getString(ARG_SECTION_CODE));
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
    public String getTitle() {
        return getString(R.string.title_form, section.getSectionCode());
    }

    @Override
    public void showQuestionDetails(int index) {
        navigateTo(QuestionsDetailsFragment.newInstance(section.getSectionCode(), index));
    }
}
