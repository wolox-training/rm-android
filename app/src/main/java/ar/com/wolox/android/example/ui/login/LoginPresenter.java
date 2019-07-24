package ar.com.wolox.android.example.ui.login;

import android.util.Log;
import android.util.Patterns;

import java.util.Objects;

import javax.inject.Inject;

import ar.com.wolox.android.example.utils.UserSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;

/**
 *
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private UserSession mUserSession;

    @Inject
    public LoginPresenter(UserSession mUserSession) {
        this.mUserSession = mUserSession;
    }

    /**
     *
     * @param username Username
     * @param password Password
     */
    public void onLoginButtonClicked(String username, String password) {
        try {
            if (this.mUserSession.getUsername() == null ||
                    this.mUserSession.getPassword() == null) {
                if (username.isEmpty() || password.isEmpty()) {
                    if (username.isEmpty()) {
                        getView().onEmptyUsername();
                    }
                    if (password.isEmpty()) {
                        getView().onEmptyPassword();
                    }
                } else if (!evaluateUsernameFormat(username)) {
                    getView().onWrongUsernameFormat();
                } else {
                    mUserSession.setUsername(username);
                    mUserSession.setPassword(password);
                    getView().goToHomePageScreen();
                }
            } else {
                getView().goToHomePageScreen();
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

    private Boolean evaluateUsernameFormat(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
