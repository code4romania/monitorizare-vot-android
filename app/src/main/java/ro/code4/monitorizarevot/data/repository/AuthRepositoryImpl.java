package ro.code4.monitorizarevot.data.repository;

import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.data.datasource.ApiDataSource;
import ro.code4.monitorizarevot.data.datasource.DataSourceFactory;
import ro.code4.monitorizarevot.domain.exception.MessageType;
import ro.code4.monitorizarevot.domain.exception.VoteException;
import ro.code4.monitorizarevot.domain.params.LoginDataParams;
import ro.code4.monitorizarevot.domain.repository.AuthRepository;
import ro.code4.monitorizarevot.net.model.User;
import rx.Observable;

@Singleton
public class AuthRepositoryImpl implements AuthRepository {

    private final DataSourceFactory mDataSourceFactory;

    @Inject
    public AuthRepositoryImpl(DataSourceFactory dataSourceFactory) {
        mDataSourceFactory = dataSourceFactory;
    }

    @Override
    public Observable<Boolean> login(LoginDataParams params) {
        if(TextUtils.isEmpty(params.getPhoneNumber()) || TextUtils.isEmpty(params.getPinNumber())) {
            return Observable.error(new VoteException(MessageType.EMPTY_CREDENTIALS));
        }

        ApiDataSource apiDataSource = mDataSourceFactory.dataSource(params.isLocal());
        User user = new User(params.getPhoneNumber(), params.getPinNumber(), params.getUdid());

        return apiDataSource.login(user);
    }
}
