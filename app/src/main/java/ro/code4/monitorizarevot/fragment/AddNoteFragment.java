package ro.code4.monitorizarevot.fragment;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ro.code4.monitorizarevot.App;
import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.Note;
import ro.code4.monitorizarevot.observable.GeneralSubscriber;
import ro.code4.monitorizarevot.util.NetworkUtils;
import ro.code4.monitorizarevot.viewmodel.AddNoteViewModel;
import ro.code4.monitorizarevot.widget.FileSelectorButton;
import vn.tungdx.mediapicker.MediaItem;
import vn.tungdx.mediapicker.MediaOptions;
import vn.tungdx.mediapicker.activities.MediaPickerActivity;

import static android.app.Activity.RESULT_OK;

public class AddNoteFragment extends BaseFragment<AddNoteViewModel> {

    private static final String ARGS_QUESTION_ID = "QuestionId";

    private static final int PERMISSIONS_READ_EXTERNAL_STORAGE = 1001;

    private static final int REQUEST_MEDIA = 100;

    private Integer questionId;

    private EditText description;

    private FileSelectorButton fileSelectorButton;

    private MediaItem mediaItem;

    public static AddNoteFragment newInstance() {
        return new AddNoteFragment();
    }

    public static AddNoteFragment newInstance(Integer questionId) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_QUESTION_ID, questionId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionId = getArguments().getInt(ARGS_QUESTION_ID);
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_note);
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(AddNoteViewModel.class);
    }

    private void openMediaPicker() {
        MediaOptions.Builder optionsBuilder = new MediaOptions.Builder();
        optionsBuilder.canSelectBothPhotoVideo();
        MediaPickerActivity.open(AddNoteFragment.this, REQUEST_MEDIA, optionsBuilder.build());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA && resultCode == RESULT_OK) {
            List<MediaItem> mediaSelectedList = MediaPickerActivity
                    .getMediaItemSelected(data);
            if (mediaSelectedList != null && mediaSelectedList.size() > 0) {
                mediaItem = mediaSelectedList.get(0);
                fileSelectorButton.setText(getFileName(mediaItem));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_READ_EXTERNAL_STORAGE: {
                if (hasGrantedPermission(grantResults)) {
                    openMediaPicker();
                } else {
                    Toast.makeText(App.getContext(), "Permisiunea este necesară pentru a putea selecta o resursă", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);

        description = rootView.findViewById(R.id.note_description);
        fileSelectorButton = rootView.findViewById(R.id.note_file_selector);

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

        rootView.findViewById(R.id.button_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().toString().length() == 0 && mediaItem == null) {
                    Toast.makeText(getActivity(), getString(R.string.invalid_note), Toast.LENGTH_SHORT).show();
                } else {
                    saveNote(mediaItem);
                    Toast.makeText(getActivity(), getString(R.string.note_saved), Toast.LENGTH_SHORT).show();
                    navigateBack();
                }
            }
        });

        return rootView;
    }

    private void saveNote(MediaItem item) {
        Note note = Data.getInstance().saveNote(
                mediaItem != null ? item.getPathOrigin(getActivity()) : null,
                description.getText().toString(),
                questionId);
        syncCurrentNote(note);
    }

    private void syncCurrentNote(Note note) {
        if (NetworkUtils.isOnline(getActivity())) {
            NetworkService.syncCurrentNote(note).startRequest(new GeneralSubscriber());
        }
    }

    private String getFileName(MediaItem item) {
        String path = item.getPathOrigin(getActivity());
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
