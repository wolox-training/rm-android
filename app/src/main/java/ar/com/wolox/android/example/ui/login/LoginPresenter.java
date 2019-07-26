package ar.com.wolox.android.example.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import ar.com.wolox.android.example.model.User;
import ar.com.wolox.android.example.network.LoginService;
import ar.com.wolox.android.example.utils.UserSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private UserSession mUserSession;
    private RetrofitServices mRetrofitServices;

    @Inject
    public LoginPresenter(UserSession mUserSession, RetrofitServices mRetrofitServices) {
        this.mUserSession = mUserSession;
        this.mRetrofitServices = mRetrofitServices;
    }

    /**
     *
     * @param username Username
     * @param password Password
     */
    public void onLoginButtonClicked(@NonNull String username, @NonNull String password) {
        try {
            if (username.isEmpty() && password.isEmpty()) {
                getView().onEmptyUsernameAndPassword();
            } else if (username.isEmpty()) {
                getView().onEmptyUsername();
            } else if (password.isEmpty()) {
                getView().onEmptyPassword();
            } else if (evaluateUsernameFormat(username)) {
                validateUser(username, password);
            } else {
                getView().onWrongUsernameFormat();
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), Objects.requireNonNull(e.getMessage()));
        }
    }

    public void onSignUpButtonClicked() {
        getView().goToSignUpScreen();
    }

    public void onTermsConditionsButtonClicked() {
        getView().goToTermsConditionsScreen();
    }

    public void saveFormBeforeDestroy(@NonNull String username) {
        if (username.isEmpty() && mUserSession.getUsername() != null) {
            mUserSession.setUsername(null);
        } else {
            mUserSession.setUsername(username);
        }
    }

    private Boolean evaluateUsernameFormat(@NonNull String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void restoreFormOnInit() {
        if (mUserSession.getUsername() != null) {
            getView().onUsernameAlreadyStored(mUserSession.getUsername());
        }
    }

    private void validateUser(@NonNull String username, @NonNull String password) {
        mRetrofitServices.getService(LoginService.class).getUserByCredentials(username, password).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                assert response.body() != null;
                if (response.body().size() > 0) {
                    mUserSession.setUsername(response.body().get(0).getEmail());
                    mUserSession.setPassword(response.body().get(0).getPassword());
                    getView().goToHomePageScreen();
                } else {
                    Log.d(getClass().getSimpleName(), "validateUser: NOT Ok");
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                Log.e(getClass().getSimpleName(), Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public void onViewAttached() {
        restoreFormOnInit();
    }
}