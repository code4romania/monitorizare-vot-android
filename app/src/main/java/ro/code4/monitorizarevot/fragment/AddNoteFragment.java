package ro.code4.monitorizarevot.fragment;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ro.code4.monitorizarevot.App;
import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.BuildConfig;
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

    /**
     * Holds info about a pick-type item
     */
    private static class ImagePickType {
        final int requestCode;
        final int menuResource;
        final String permission;

        MenuItem mItem; // accessible anyway from this file, not bothering encapsulating

        ImagePickType(int requestCode, int menuResource, String permission) {
            this.requestCode = requestCode;
            this.menuResource = menuResource;
            this.permission = permission;
        }
    }

    private static final int REQUEST_CODE_RECORD_VIDEO = 1001;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1002;
    private static final int REQUEST_CODE_GALLERY = 1003;

    private static final ImagePickType[] PICK_TYPES = new ImagePickType[] {
            new ImagePickType(REQUEST_CODE_GALLERY, R.string.note_gallery, Manifest.permission.READ_EXTERNAL_STORAGE),
            new ImagePickType(REQUEST_CODE_TAKE_PHOTO, R.string.note_take_photo, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            new ImagePickType(REQUEST_CODE_RECORD_VIDEO, R.string.note_record_video, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    };

    private static final String ARGS_QUESTION_ID = "QuestionId";

    private static final String TAG = "AddNoteFragment";

    private Integer questionId;

    private EditText description;

    private FileSelectorButton fileSelectorButton;

    private File mFile;

    private PopupMenu mPickImageMenu;

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

    /**
     * Generates an image-picking intent. It's Android version dependent. Thanks to epforgpl.
     */
    private void pickMedia() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/* video/*");
            startActivityForResult(intent, REQUEST_CODE_GALLERY);
            return;
        }
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    /**
     * Creates a media file path for the new image/video. Thanks to epforgpl.
     */
    private static File createMediaFile(String name, String folder) {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(folder), "MonitorizareVot");
        if (!storageDir.exists()) {
            storageDir.mkdirs();    // result ignored
        }

        return new File(storageDir, name);
    }

    /**
     * Returns the next value to store in the timestamp string for the image or video.
     */
    private static String getNextTimestampString() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    /**
     * Take photo and prepares the file
     */
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getContext() != null && takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File file = createMediaFile("IMG_" + getNextTimestampString() + ".jpg",
                    Environment.DIRECTORY_PICTURES);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        file);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            mFile = file;
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    /**
     * Records video and prepares the file
     */
    private void recordVideo() {
        Intent recordIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (getContext() != null && recordIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File file = createMediaFile("VID_"+ getNextTimestampString() + ".mp4", Environment.DIRECTORY_MOVIES);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        file);
                recordIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                recordIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            recordIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            mFile = file;
            startActivityForResult(recordIntent, REQUEST_CODE_RECORD_VIDEO);
        }
    }

    /**
     * When it should actually do the action
     */
    private void doImageAction(int code) {
        switch (code) {
            case REQUEST_CODE_GALLERY:
                pickMedia();
                break;
            case REQUEST_CODE_TAKE_PHOTO:
                takePhoto();
                break;
            case REQUEST_CODE_RECORD_VIDEO:
                recordVideo();
                break;
        }
    }

    /**
     * Checks permission and if possible does the action.
     */
    private void tryImageAction(ImagePickType type) {
        if (hasPermission(type.permission)) {
            doImageAction(type.requestCode);
        } else {
            requestPermission(type.permission, type.requestCode);
        }
    }

    /**
     * Builds the popup menu if not already built
     */
    private PopupMenu getPickImageMenu() {
        if (mPickImageMenu != null) {
            return mPickImageMenu;
        }
        if (getContext() == null) {
            return null;
        }
        mPickImageMenu = new PopupMenu(getContext(), fileSelectorButton);
        Menu menu = mPickImageMenu.getMenu();
        int count = 0;
        for(ImagePickType type : PICK_TYPES) {
            menu.add(type.menuResource);
            type.mItem = menu.getItem(count++); // need to set it now
        }
        mPickImageMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for(ImagePickType type : PICK_TYPES) {
                    if (type.mItem == item) {
                        tryImageAction(type);
                    }
                }
                return true;
            }
        });
        return mPickImageMenu;
    }

    /**
     * Add media to gallery. Thanks to epforgpl.
     */
    private void galleryAddMedia() {
        if (getContext() == null) {
            Log.e(TAG, "AddMedia: context is null");
            return;
        }
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(mFile);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    /**
     * When image is obtained from gallery, load it, making sure it's OK by path.
     * @param uri The URI of the image.
     */
    private void useImageFromGallery(Uri uri) {
        if (uri == null) {
            Toast.makeText(App.getContext(), R.string.error_permission_external_storage, Toast.LENGTH_LONG).show();
        }

        String filePath = FileUtils.getPath(getContext(), uri);
        if (filePath != null) {
            File file = new File(filePath);
            fileSelectorButton.setText(file.getName());
            mFile = file;
        } else {
            Toast.makeText(App.getContext(), R.string.error_permission_external_storage, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * When ANY of the image-getter activities is returned.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {
            return;
        }
        for(ImagePickType type : PICK_TYPES) {
            if (requestCode != type.requestCode) {
                continue;
            }
            if (type.requestCode == REQUEST_CODE_GALLERY) {
                useImageFromGallery(data.getData());
                break;
            }
            // live photo or video (not gallery)
            if (mFile != null) {
                fileSelectorButton.setText(mFile.getName());
                galleryAddMedia();
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for(ImagePickType type : PICK_TYPES) {
            if (type.requestCode != requestCode) {
                continue;
            }
            if (hasGrantedPermission(grantResults)) {
                doImageAction(type.requestCode);
            } else {
                Toast.makeText(App.getContext(), R.string.error_permission_external_storage, Toast.LENGTH_LONG).show();
            }
            break;
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
                getPickImageMenu().show();  // shouldn't be null
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
