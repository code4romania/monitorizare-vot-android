package ro.code4.votehack.net;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import ro.code4.votehack.util.Logify;

public abstract class HttpCallback<T> implements Callback {
    private Class<T> tClass;

    public HttpCallback(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        onError();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected response " + response);

        String bodyString = response.body().string();

        Logify.d("Http Response", bodyString);

        onSuccess(new Gson().fromJson(bodyString, tClass));
    }

    public abstract void onSuccess(T response);

    public abstract void onError();
}
