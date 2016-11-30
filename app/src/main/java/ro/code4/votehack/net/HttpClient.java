package ro.code4.votehack.net;

import com.google.gson.Gson;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import ro.code4.votehack.net.model.ResponseAnswerContainer;

public class HttpClient {
    private static HttpClient instance;
    private OkHttpClient client;

    private HttpClient() {
        client = new OkHttpClient();
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    public void getForm(String formId, Callback callback) {
        Request request = new Request.Builder()
                .url("http://viuat.azurewebsites.net/api/v1/formular?idformular=" + formId)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void getFormVersion(Callback callback) {
        Request request = new Request.Builder()
                .url("http://viuat.azurewebsites.net/api/v1/formular/versiune")
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void postQuestionAnswer(Callback callback, ResponseAnswerContainer response){
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(response));

        Request request = new Request.Builder()
                .url("http://viuat.azurewebsites.net/api/v1/raspuns")
                .method("POST", body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
