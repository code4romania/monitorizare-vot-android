package ro.code4.monitorizarevot.dagger;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ro.code4.monitorizarevot.BuildConfig;
import ro.code4.monitorizarevot.net.AuthInterceptor;

@Module
public class RetrofitModule {

    @Provides
    @Singleton
    @Named("logging_interceptor")
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    @Singleton
    @Named("retrofit")
    static Retrofit provideRetrofit(@Named("logging_interceptor") HttpLoggingInterceptor loggingInterceptor, AuthInterceptor authInterceptor) {
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

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.WEB_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
