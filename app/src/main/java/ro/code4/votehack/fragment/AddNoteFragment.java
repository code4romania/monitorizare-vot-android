package ro.code4.votehack.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import ro.code4.votehack.BaseFragment;
import ro.code4.votehack.R;
import ro.code4.votehack.widget.FileSelectorButton;
import vn.tungdx.mediapicker.MediaOptions;
import vn.tungdx.mediapicker.activities.MediaPickerActivity;

public class AddNoteFragment extends BaseFragment {
    private static final String ARGS_QUESTION_ID = "QuestionId";
    private static final int PERMISSIONS_READ_EXTERNAL_STORAGE = 1001;
    private static final int REQUEST_MEDIA = 100;
    private Integer questionId;
    private EditText description;
    private FileSelectorButton fileSelectorButton;

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

        description = (EditText) rootView.findViewById(R.id.note_description);
        fileSelectorButton = (FileSelectorButton) rootView.findViewById(R.id.note_file_selector);

        fileSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    openMediaPicker();
                } else {
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                            PERMISSIONS_READ_EXTERNAL_STORAGE);
                }
            }
        });

        return rootView;
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_note);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_READ_EXTERNAL_STORAGE: {
                if (hasGrantedPermission(grantResults)) {
                    openMediaPicker();
                } else {
                    navigateBack();
                    Toast.makeText(getActivity(), "Permisiunea este necesară pentru a putea selecta o resursă", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void openMediaPicker() {
        MediaOptions.Builder optionsBuilder = new MediaOptions.Builder();
        optionsBuilder.canSelectBothPhotoVideo();
        MediaPickerActivity.open(AddNoteFragment.this, REQUEST_MEDIA, optionsBuilder.build());
    }
}
