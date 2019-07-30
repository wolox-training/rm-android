package ar.com.wolox.android.example.ui.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import ar.com.wolox.android.R;
import ar.com.wolox.android.example.model.User;
import ar.com.wolox.android.example.network.LoginService;
import ar.com.wolox.android.example.utils.UserSession;
import ar.com.wolox.wolmo.core.util.ToastFactory;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;

@RunWith(JUnit4.class)
public class LoginPresenterTest {

    @Mock ToastFactory mToastFactory;
    @Mock RetrofitServices mRetrofitServices;
    @Mock ILoginView mILoginView;
    @Mock UserSession mUserSession;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mLoginPresenter = new LoginPresenter(mUserSession, mRetrofitServices, mToastFactory);
    }

    @Test
    public void usernameEmptyField(){
        mLoginPresenter.attachView(mILoginView);
        mLoginPresenter.onLoginButtonClicked("","123");
        Mockito.verify(mILoginView).onEmptyUsername();
    }

    @Test
    public void passwordEmptyField(){
        mLoginPresenter.attachView(mILoginView);
        mLoginPresenter.onLoginButtonClicked("algo@algo.com","");
        Mockito.verify(mILoginView).onEmptyPassword();
    }

    @Test
    public void usernameAndPasswordEmptyFields(){
        mLoginPresenter.attachView(mILoginView);
        mLoginPresenter.onLoginButtonClicked("","");
        Mockito.verify(mILoginView).onEmptyUsernameAndPassword();
    }

    @Test
    public void wrongUsernameFormat() {
        mLoginPresenter.attachView(mILoginView);
        mLoginPresenter.onLoginButtonClicked("algo.com","12345678");
        Mockito.verify(mILoginView).onWrongUsernameFormat();
    }

    @Test
    public void onCorrectLogin() {
        mLoginPresenter.attachView(mILoginView);
        List<User> responseUser = new ArrayList<>();
        responseUser.add(new User());
        Call<List<User>> mockedResponse = (Call<List<User>>) Mockito.mock(Call.class);
        LoginService mockedApiInterface = Mockito.mock(LoginService.class);

        Mockito.when(mRetrofitServices.getService(LoginService.class)).thenReturn(mockedApiInterface);
        Mockito.when(mockedApiInterface.getUserByCredentials("susan.stevens38@example.com","12345678")).thenReturn(mockedResponse);

        Mockito.doAnswer(invocation -> {
            Callback<List<User>> callback = invocation.getArgument(0);
            callback.onResponse(mockedResponse, Response.success(responseUser));
            return null;
        }).when(mockedResponse).enqueue(any(Callback.class));

        mLoginPresenter.getUserByCredentials("susan.stevens38@example.com","12345678");

        Mockito.verify(mILoginView).showProgressBar();
        Mockito.verify(mILoginView).hideProgressBar();
        Mockito.verify(mILoginView).goToHomePageScreen();
    }

    @Test
    public void onIncorrectLogin() {
        mLoginPresenter.attachView(mILoginView);
        List<User> responseUser = new ArrayList<>();
        Call<List<User>> mockedResponse = (Call<List<User>>) Mockito.mock(Call.class);
        LoginService mockedApiInterface = Mockito.mock(LoginService.class);

        Mockito.when(mRetrofitServices.getService(LoginService.class)).thenReturn(mockedApiInterface);
        Mockito.when(mockedApiInterface.getUserByCredentials("susan.stevens38@example.com","123")).thenReturn(mockedResponse);

        Mockito.doAnswer(invocation -> {
            Callback<List<User>> callback = invocation.getArgument(0);
            callback.onResponse(mockedResponse, Response.success(responseUser));
            return null;
        }).when(mockedResponse).enqueue(any(Callback.class));

        mLoginPresenter.getUserByCredentials("susan.stevens38@example.com","123");

        Mockito.verify(mILoginView).showProgressBar();
        Mockito.verify(mILoginView).hideProgressBar();
        Mockito.verify(mToastFactory, Mockito.times(1)).show(R.string.login_error_username_password);
    }

    @Test
    public void onServiceFail(){
        mLoginPresenter.attachView(mILoginView);
        Call<List<User>> mockedResponse = (Call<List<User>>) Mockito.mock(Call.class);
        Throwable mockedThrowable = Mockito.mock(Throwable.class);
        LoginService mockedApiInterface = Mockito.mock(LoginService.class);

        Mockito.when(mRetrofitServices.getService(LoginService.class)).thenReturn(mockedApiInterface);
        Mockito.when(mockedApiInterface.getUserByCredentials("susan.stevens38@example.com","12345678")).thenReturn(mockedResponse);

        Mockito.doAnswer(invocation -> {
            Callback<List<User>> callback = invocation.getArgument(0);
            callback.onFailure(mockedResponse, mockedThrowable);
            return null;
        }).when(mockedResponse).enqueue(any(Callback.class));

        mLoginPresenter.getUserByCredentials("susan.stevens38@example.com","12345678");

        Mockito.verify(mILoginView).showProgressBar();
        Mockito.verify(mILoginView).hideProgressBar();
        Mockito.verify(mToastFactory, Mockito.times(1)).show(R.string.login_error_service_message);
    }
}