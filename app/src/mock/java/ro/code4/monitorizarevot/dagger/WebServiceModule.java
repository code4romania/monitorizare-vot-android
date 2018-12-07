package ro.code4.monitorizarevot.dagger;

import dagger.Binds;
import dagger.Module;
import ro.code4.monitorizarevot.data.rest.ApiService;
import ro.code4.monitorizarevot.data.rest.MockApiService;

@Module
public abstract class WebServiceModule {

    @Binds
    abstract ApiService bindsApiService(MockApiService service);
}
