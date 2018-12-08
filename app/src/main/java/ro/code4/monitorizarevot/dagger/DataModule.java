package ro.code4.monitorizarevot.dagger;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.code4.monitorizarevot.data.chat.socket.DummySocketConnector;
import ro.code4.monitorizarevot.data.chat.socket.SocketConnector;
import ro.code4.monitorizarevot.data.datasource.ApiDataSource;
import ro.code4.monitorizarevot.data.datasource.HttpDataSource;
import ro.code4.monitorizarevot.data.datasource.LocalDataSource;

@Module(includes = WebServiceModule.class)
public abstract class DataModule {

    @Binds
    @IntoMap
    @DataSourceKey(LocalDataSource.class)
    abstract ApiDataSource bindLocalDataSource(LocalDataSource dataSource);

    @Binds
    @IntoMap
    @DataSourceKey(HttpDataSource.class)
    abstract ApiDataSource bindHttpDataSource(HttpDataSource dataSource);

    @Binds

    abstract SocketConnector bindSocketConnector(DummySocketConnector socketConnector);
}
