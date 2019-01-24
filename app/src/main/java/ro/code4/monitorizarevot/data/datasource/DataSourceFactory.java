package ro.code4.monitorizarevot.data.datasource;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.util.AbstractComponentFactory;

@Singleton
public class DataSourceFactory extends AbstractComponentFactory<ApiDataSource> {

    @Inject
    public DataSourceFactory(Map<Class<? extends ApiDataSource>, Provider<ApiDataSource>> creators) {
        super(creators);
    }

    public ApiDataSource localSource() {
        return create(LocalDataSource.class);
    }

    public ApiDataSource httpSource() {
        return create(HttpDataSource.class);
    }

    public ApiDataSource dataSource(boolean isLocal) {
        return isLocal ? localSource() : httpSource();
    }
}
