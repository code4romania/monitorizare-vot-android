package ro.code4.votehack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;

public class AddNoteFragment extends BaseFragment {
    public static final String ARGS_QUESTION_ID = "QuestionId";
    public static Integer questionId;

    public static AddNoteFragment newInstance() {
        return new AddNoteFragment();
    }

    public static AddNoteFragment newInstance(Integer idIntrebare) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_QUESTION_ID, idIntrebare);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionId = getArguments().getInt(ARGS_QUESTION_ID);
            Toast.makeText(getActivity(), getArguments().getInt(ARGS_QUESTION_ID) + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        return rootView;
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_note);
    }
}
