package ro.code4.monitorizarevot.net;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ro.code4.monitorizarevot.BuildConfig;
import ro.code4.monitorizarevot.constants.Auth;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.Note;
import ro.code4.monitorizarevot.net.model.QuestionAnswer;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;
import ro.code4.monitorizarevot.observable.ObservableRequest;
import ro.code4.monitorizarevot.util.AuthUtils;
import rx.Subscriber;

public class NetworkService {
    // TODO multipart names to be translated
    private static final int NUMBER_OF_RETRIES = 3;
    private static final String MULTIPART_NAME = "file";
    private static final String MEDIA_TYPE_MULTIPART = "multipart/form-data";
    private static final String MULTIPART_COUNTY = "CodJudet";
    private static final String MULTIPART_BRANCH = "NumarSectie";
    private static final String MULTIPART_QUESTION = "IdIntrebare";
    private static final String MULTIPART_NOTE = "TextNota";
    private static final String API_KEY_ERROR = "error";

    private static ApiService mApiService;

    private static ApiService getApiService() {
        if (mApiService == null) {
            mApiService = initRetrofitInstanceWithUrl(BuildConfig.WEB_BASE_URL).create(ApiService.class);
        }
        return mApiService;
    }

    private static Retrofit initRetrofitInstanceWithUrl(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new AuthInterceptor());
        OkHttpClient client = clientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public static void doGetForm(String formId, int retries) throws IOException {
        Response<List<Section>> listResponse = getApiService().getForm(formId).execute();
        if(listResponse != null){
            if (listResponse.isSuccessful()) {
                Data.getInstance().saveFormDefinition(formId, listResponse.body());
            } else {
                if (retries > 0) {
                    doGetForm(formId, retries - 1);
                } else {
                    throw new IOException(listResponse.message() + " " + listResponse.code());
                }
            }
        } else {
            throw new IOException();
        }
    }

    public static ObservableRequest<Boolean> doGetForm(final String formId) {
        return new ObservableRequest<>(new ObservableRequest.OnRequested<Boolean>() {
            @Override
            public void onRequest(Subscriber<? super Boolean> subscriber) {
                try {
                    doGetForm(formId, NUMBER_OF_RETRIES);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (IOException e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    public static VersionResponse doGetFormVersion() throws IOException {
        Response<VersionResponse> response = getApiService().getFormVersion().execute();
        if(response != null){
            if(response.isSuccessful()){
                return response.body();
            } else {
                throw new IOException(response.message() + " " + response.code());
            }
        } else {
            throw new IOException();
        }
    }

    public static Ack postBranchDetails(BranchDetails branchDetails) throws IOException {
        Response<Ack> response = getApiService().postBranchDetails(branchDetails).execute();
        if(response != null){
            if(response.isSuccessful()){
                return response.body();
            } else {
                throw new IOException(response.message() + " " + response.code());
            }
        } else {
            throw new IOException();
        }
    }

    public static QuestionResponse postQuestionAnswer(ResponseAnswerContainer responseMapper) throws IOException {
        if(responseMapper != null && responseMapper.getResponseMapperList().size()>0){
            Response<QuestionResponse> response = getApiService().postQuestionAnswer(responseMapper).execute();
            if(response != null){
                if(response.isSuccessful()){
                    for (QuestionAnswer questionAnswer : responseMapper.getResponseMapperList()) {
                        Data.getInstance().updateQuestionStatus(questionAnswer.getQuestionId());
                    }
                    return response.body();
                } else {
                    throw new IOException(response.message() + " " + response.code());
                }
            } else {
                throw new IOException();
            }
        } else {
            return null;
        }
    }

    public static ResponseNote postNote(Note note) throws IOException {
        MultipartBody.Part body = null;
        if (note.getUriPath() != null) {
            File file = new File(note.getUriPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse(MEDIA_TYPE_MULTIPART), file);
            body = MultipartBody.Part.createFormData(MULTIPART_NAME, file.getName(), requestFile);
        }
        Response<ResponseNote> response = getApiService().postNote(body,
                createMultipart(MULTIPART_COUNTY, Preferences.getCountyCode()),
                createMultipart(MULTIPART_BRANCH, Preferences.getBranchNumber()),
                createMultipart(MULTIPART_QUESTION, note.getQuestionId() != null ? note.getQuestionId() : 0),
                createMultipart(MULTIPART_NOTE, note.getDescription())).execute();
        if (response != null) {
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new IOException();
            }
        } else {
            throw new IOException();
        }
    }

    private static MultipartBody.Part createMultipart(String name, int number) {
        return createMultipart(name, String.valueOf(number));
    }

    private static MultipartBody.Part createMultipart(String name, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE_MULTIPART), value);
        return MultipartBody.Part.createFormData(name, null, requestBody);
    }

    public static ObservableRequest<Boolean> login(final User user) {
        return new ObservableRequest<>(new ObservableRequest.OnRequested<Boolean>() {
            @Override
            public void onRequest(Subscriber<? super Boolean> subscriber) {
                try {
                    Response<Object> response = getApiService().postAuth(user).execute();
                    if(response.isSuccessful()){
                        JSONObject body = new JSONObject(response.body().toString());
                        String token = body.has(Auth.ACCESS_TOKEN) ? body.getString(Auth.ACCESS_TOKEN) : null;
                        if(token != null){
                            AuthUtils.initAccount(user.getPhone(), user.getPin(), token);

                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new IOException("Server error"));
                        }
                    } else {
                        JSONObject jsonErrorMessage = new JSONObject(response.errorBody().string());
                        String message = jsonErrorMessage.has(API_KEY_ERROR) ?
                                jsonErrorMessage.getString(API_KEY_ERROR) : response.message();
                        subscriber.onError(new IOException(message));
                    }
                } catch (IOException e){
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (JSONException e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    public static ObservableRequest<Boolean> syncCurrentQuestion(final QuestionAnswer questionAnswer) {
        return new ObservableRequest<>(new ObservableRequest.OnRequested<Boolean>() {
            @Override
            public void onRequest(Subscriber<? super Boolean> subscriber) {
                try {
                    ResponseAnswerContainer responseMapper = new ResponseAnswerContainer(Arrays.asList(questionAnswer));
                    postQuestionAnswer(responseMapper);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (IOException e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }


    public static ObservableRequest<Boolean>  syncCurrentNote(final Note note) {
        return new ObservableRequest<>(new ObservableRequest.OnRequested<Boolean>() {
            @Override
            public void onRequest(Subscriber<? super Boolean> subscriber) {
                try {
                    postNote(note);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (IOException e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
