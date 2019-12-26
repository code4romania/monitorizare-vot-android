package ro.code4.monitorizarevot.data.repository;

import android.telephony.PhoneNumberUtils;

import static android.text.TextUtils.isEmpty;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
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
        if(params == null || isEmpty(params.getPhoneNumber()) || isEmpty(params.getPinNumber())) {
            return Observable.error(new VoteException(MessageType.EMPTY_CREDENTIALS));
        }

        if(!PhoneNumberUtils.isGlobalPhoneNumber(params.getPhoneNumber())) {
            return Observable.error(new VoteException(MessageType.INVALID_PHONE_NUMBER));
        }

        ApiDataSource apiDataSource = mDataSourceFactory.dataSource(params.isLocal());
        User user = new User(params.getPhoneNumber().replaceAll("[^+\\d.]", ""), params.getPinNumber(), params.getUdid());

        return apiDataSource.login(user);
    }
}
