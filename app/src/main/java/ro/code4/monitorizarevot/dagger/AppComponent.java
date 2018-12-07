package ro.code4.monitorizarevot.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import ro.code4.monitorizarevot.App;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class
})
public interface AppComponent {

    void inject(App application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
