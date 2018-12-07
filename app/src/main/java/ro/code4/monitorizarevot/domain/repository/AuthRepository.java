package ro.code4.monitorizarevot.domain.repository;

import ro.code4.monitorizarevot.domain.params.LoginDataParams;
import rx.Observable;

public interface AuthRepository extends DataRepository {

    Observable<Boolean> login(LoginDataParams params);
}
