package ro.code4.monitorizarevot.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import ro.code4.monitorizarevot.data.datasource.ApiDataSource;
import ro.code4.monitorizarevot.data.datasource.DataSourceFactory;
import ro.code4.monitorizarevot.domain.exception.VoteException;
import ro.code4.monitorizarevot.domain.params.LoginDataParams;
import ro.code4.monitorizarevot.net.model.User;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthRepositoryImplTest {

    private AuthRepositoryImpl authRepository;

    @Mock
    private DataSourceFactory mockDataSourceFactory;

    @Mock
    private ApiDataSource mockApiDataSource;

    @Mock
    private User mockUser;

    private static final String PHONE_NO = "0744123789";

    private static final String PIN = "1234";

    private static final String UUID = "9cc14ea0-2174-11e9-ab14-d663bd873d93";


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authRepository = new AuthRepositoryImpl(mockDataSourceFactory);
    }

    @Test
    public void shouldNotLoginOnNullInput() {
        //when
        Observable<Boolean> loginResult = authRepository.login(null);

        //then
        loginResult.test().assertError(VoteException.class);
    }

    @Test
    public void shouldNotLoginOnEmptyPhoneNumber() {
        //given
        LoginDataParams loginDataParams = new LoginDataParams(null, null, null);

        //when
        Observable<Boolean> loginResult = authRepository.login(loginDataParams);

        //then
        loginResult.test().assertError(VoteException.class);
    }

    @Test
    public void shouldNotLoginOnEmptyPinNumber() {
        //given
        LoginDataParams loginDataParams = new LoginDataParams(PHONE_NO, null, null);

        //when
        Observable<Boolean> loginResult = authRepository.login(loginDataParams);

        //then
        loginResult.test().assertError(VoteException.class);
    }

    @Test
    public void shouldLogin() {
        //given
        LoginDataParams loginDataParams = new LoginDataParams(PHONE_NO, PIN, UUID);
        when(mockDataSourceFactory.dataSource(anyBoolean())).thenReturn(mockApiDataSource);

        //when
        authRepository.login(loginDataParams);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(mockApiDataSource).login(argument.capture());


        //then
        assertEquals(loginDataParams.getPhoneNumber(), argument.getValue().getPhone());
        assertEquals(loginDataParams.getPinNumber(), argument.getValue().getPin());
        assertEquals(loginDataParams.getUdid(), argument.getValue().getUdid());
    }
}