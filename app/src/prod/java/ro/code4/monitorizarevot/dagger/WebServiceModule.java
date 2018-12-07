package ro.code4.monitorizarevot.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ro.code4.monitorizarevot.data.rest.ApiService;

@Module(includes = RetrofitModule.class)
public class WebServiceModule {

    @Provides
    @Singleton
    static ApiService provideApiService(@Named("retrofit") Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
