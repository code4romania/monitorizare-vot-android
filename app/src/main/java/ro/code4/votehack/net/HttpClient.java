package ro.code4.votehack.net;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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

    public void getForm(Callback callback) {
        getForm("", callback);
    }

    public void getForm(String formId, Callback callback) {
        Request request = new Request.Builder()
                .url("http://viuat.azurewebsites.net/api/v1/formular")
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void getFormVersion(Callback callback) {
        Request request = new Request.Builder()
                .url("http://viuat.azurewebsites.net/api/v1/formular/versiune")
                .build();
        client.newCall(request).enqueue(callback);
    }
}
