package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.util.FormRenderer;

public class FormDetailsFragment extends BaseFragment {
    private Section section;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ViewGroup detailsContainer = (ViewGroup) rootView.findViewById(R.id.details_container);
        detailsContainer.addView(FormRenderer.render(getActivity(), detailsContainer, section));
        return rootView;
    }

    @Override
    public String getIdentifier() {
        return "FormDetailsFragment";
    }
}
