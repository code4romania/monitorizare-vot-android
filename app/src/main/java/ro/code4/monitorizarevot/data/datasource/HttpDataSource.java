package ro.code4.monitorizarevot.data.datasource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import retrofit2.Response;
import ro.code4.monitorizarevot.data.rest.ApiService;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.domain.exception.MessageType;
import ro.code4.monitorizarevot.domain.exception.VoteException;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.Note;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;
import ro.code4.monitorizarevot.util.AuthUtils;
import rx.Observable;
import rx.functions.Func1;

@Singleton
public class HttpDataSource implements ApiDataSource {

    private static final int NUMBER_OF_RETRIES = 3;

    private static final String ACCESS_TOKEN = "access_token";

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
    @SuppressWarnings({"unchecked"})
    public Observable<Boolean> login(final User user) {
        return mApiService.postAuth(user).onErrorResumeNext(new Func1<Throwable, Observable<?>>() {

            @Override
            public Observable<Object> call(Throwable throwable) {
                if (throwable instanceof HttpException) {
                    try {
                        Response<Object> response = (Response<Object>) ((HttpException) throwable).response();

                        JSONObject jsonErrorMessage = new JSONObject(response.errorBody().string());
                        String message = jsonErrorMessage.has("error") ?
                                         jsonErrorMessage.getString("error") : response.message();

                        return Observable.error(new IOException(message));

                    } catch (JSONException | IOException e) {
                        return Observable.error(throwable);
                    }

                } else {
                    return Observable.error(throwable);
                }
            }
        }).flatMap(new Func1<Object, Observable<Boolean>>() {

            @Override
            public Observable<Boolean> call(Object response) {
                try {
                    JSONObject body = new JSONObject(response.toString());
                    String token = body.has(ACCESS_TOKEN) ? body.getString(ACCESS_TOKEN) : null;
                    if (token != null) {
                        AuthUtils.initAccount(user.getPhone(), user.getPin(), token);

                        return Observable.just(true);

                    } else {
                        return Observable.error(new VoteException(MessageType.SERVER_ERROR));
                    }
                } catch (JSONException ex) {
                    return Observable.error(ex);
                }
            }
        });
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
