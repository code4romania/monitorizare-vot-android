package ro.code4.monitorizarevot.data.datasource;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ro.code4.monitorizarevot.data.rest.ApiService;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.Note;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;
import rx.Observable;

@Singleton
public class HttpDataSource implements ApiDataSource {

    private final ApiService mApiService;

    @Inject
    public HttpDataSource(ApiService apiService) {
        mApiService = apiService;
    }

    private static MultipartBody.Part createMultipart(String name, int number) {
        return createMultipart(name, String.valueOf(number));
    }

    private static MultipartBody.Part createMultipart(String name, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), value);
        return MultipartBody.Part.createFormData(name, null, requestBody);
    }

    @Override
    public Observable<Object> postAuth(User user) {
        return mApiService.postAuth(user);
    }

    @Override
    public Observable<List<Section>> getForm(String formId) {
        return mApiService.getForm(formId);
    }

    @Override
    public Observable<VersionResponse> getFormVersion() {
        return mApiService.getFormVersion();
    }

    @Override
    public Observable<Ack> postBranchDetails(BranchDetails branchDetails) {
        return mApiService.postBranchDetails(branchDetails);
    }

    @Override
    public Observable<QuestionResponse> postQuestionAnswer(ResponseAnswerContainer responseAnswerContainer) {
        return mApiService.postQuestionAnswer(responseAnswerContainer);
    }

    @Override
    public Observable<ResponseNote> postNote(Note note) {
        MultipartBody.Part body = null;
        if (note.getUriPath() != null) {
            File file = new File(note.getUriPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }

        return mApiService.postNote(body, createMultipart("CodJudet", Preferences.getCountyCode()),
                                    createMultipart("NumarSectie", Preferences.getBranchNumber()),
                                    createMultipart("IdIntrebare", note.getQuestionId() != null ? note.getQuestionId() : 0),
                                    createMultipart("TextNota", note.getDescription()));
    }
}
