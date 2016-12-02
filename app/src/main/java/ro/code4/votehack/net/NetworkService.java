package ro.code4.votehack.net;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ro.code4.votehack.BuildConfig;
import ro.code4.votehack.db.Data;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.net.model.QuestionAnswer;
import ro.code4.votehack.net.model.ResponseAnswerContainer;
import ro.code4.votehack.net.model.response.VersionResponse;
import ro.code4.votehack.net.model.response.question.QuestionResponse;

public class NetworkService {

    private static ApiService mApiService;

    public static ApiService getApiService() {
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
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(interceptor);
        OkHttpClient client = clientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public static void doGetForm(String formId) throws IOException {
        Response<List<Section>> listResponse = getApiService().getForm(formId).execute();
        if(listResponse != null){
            if (listResponse.isSuccessful()) {
                Data.getInstance().saveFormDefinition(formId, listResponse.body());
            } else {
                throw new IOException(listResponse.message() + " " + listResponse.code());
            }
        } else {
            throw new IOException();
        }

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

    public static QuestionResponse postQuestionAnswer(ResponseAnswerContainer responseMapper) throws IOException {
        if(responseMapper != null && responseMapper.getReponseMapperList().size()>0){
            Response<QuestionResponse> response = getApiService().postQuestionAnswer(responseMapper).execute();
            if(response != null){
                if(response.isSuccessful()){
                    for (QuestionAnswer questionAnswer : responseMapper.getReponseMapperList()) {
                        Data.getInstance().updateQuestionStatus(questionAnswer.getIdIntrebare());
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

}
