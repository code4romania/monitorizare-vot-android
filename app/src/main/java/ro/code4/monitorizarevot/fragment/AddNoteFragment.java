package ro.code4.monitorizarevot.fragment;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import ro.code4.monitorizarevot.App;
import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.net.NetworkService;
import ro.code4.monitorizarevot.net.model.Note;
import ro.code4.monitorizarevot.observable.GeneralSubscriber;
import ro.code4.monitorizarevot.util.FileUtils;
import ro.code4.monitorizarevot.util.NetworkUtils;
import ro.code4.monitorizarevot.viewmodel.AddNoteViewModel;
import ro.code4.monitorizarevot.widget.FileSelectorButton;

import static android.app.Activity.RESULT_OK;

public class AddNoteFragment extends BaseFragment<AddNoteViewModel> {

    private static final String ARGS_QUESTION_ID = "QuestionId";

    private static final int PERMISSIONS_READ_EXTERNAL_STORAGE = 1001;

    private static final int REQUEST_MEDIA = 100;

    private Integer questionId;

    private EditText description;

    private FileSelectorButton fileSelectorButton;

    private File mFile;

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

        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/* video/*");
            startActivityForResult(intent, REQUEST_MEDIA);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
        startActivityForResult(intent, REQUEST_MEDIA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA && resultCode == RESULT_OK) {
            if (data == null || getContext() == null) {
                return;
            }
            Uri uri = data.getData();
            if  (uri == null) {
                return;
            }
            String path = FileUtils.getPath(getContext(), uri);
            if (path == null) {
                Toast.makeText(App.getContext(), R.string.error_permission_external_storage, Toast.LENGTH_LONG).show();
                return;
            }
            File file = new File(path);
            fileSelectorButton.setText(file.getName());
            mFile = file;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_READ_EXTERNAL_STORAGE: {
                if (hasGrantedPermission(grantResults)) {
                    openMediaPicker();
                } else {
                    // TODO: translate this message
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
                if (description.getText().toString().length() == 0 && mFile == null) {
                    Toast.makeText(getActivity(), getString(R.string.invalid_note), Toast.LENGTH_SHORT).show();
                } else {
                    saveNote();
                    Toast.makeText(getActivity(), getString(R.string.note_saved), Toast.LENGTH_SHORT).show();
                    navigateBack();
                }
            }
        });

        return rootView;
    }

    private void saveNote() {
        Note note = Data.getInstance().saveNote(
                mFile != null ? mFile.getAbsolutePath() : null,
                description.getText().toString(),
                questionId);
        syncCurrentNote(note);
    }

    private void syncCurrentNote(Note note) {
        if (getActivity() != null && NetworkUtils.isOnline(getActivity())) {
            NetworkService.syncCurrentNote(note).startRequest(new GeneralSubscriber());
        }
    }
}
