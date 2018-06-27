package ro.code4.monitorizarevot.net;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ro.code4.monitorizarevot.constants.Auth;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.LogoutListener;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws java.io.IOException {
        Response response = chain.proceed(tokenAppendedRequest(chain.request()));
        if (response.code() == 401) {
            EventBus.getDefault().post(new LogoutListener());
        }

        return response;
    }

    private Request tokenAppendedRequest(Request request) {
        String token = Preferences.getToken();
        if(token != null){
            return request
                    .newBuilder()
                    .removeHeader(Auth.KEY)
                    .addHeader(Auth.KEY, Auth.BEARER + " " + token)
                    .build();
        }
        return request;
    }
}
