package ro.code4.monitorizarevot.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        ActivityBindingModule.class
})
class AppModule {

    @Provides
    @Singleton
    static Context provideAppContext(Application application) {
        return application;
    }
}
