package ro.code4.monitorizarevot.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.data.datasource.DataSourceFactory;
import ro.code4.monitorizarevot.domain.repository.FormsRepository;

@Singleton
public class FormsRepositoryImpl implements FormsRepository {

    private final DataSourceFactory mDataSourceFactory;

    @Inject
    public FormsRepositoryImpl(DataSourceFactory dataSourceFactory) {
        mDataSourceFactory = dataSourceFactory;
    }
}
