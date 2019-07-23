package ar.com.wolox.android.example.ui.login;

import android.util.Log;

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
    public void startLogin(String username, String password) {
        try {
            if (this.mUserSession.getUsername() == null ||
                    this.mUserSession.getPassword() == null) {
                if (username.isEmpty() || password.isEmpty()) {
                    getView().onEmptyForm();
                } else if (!evaluateUsernameFormat(username)) {
                    getView().onWrongUsernameFormat();
                } else {
                    mUserSession.setUsername(username);
                    mUserSession.setPassword(password);
                    getView().onUserLoggedIn();
                }
            } else {
                Log.d(getClass().getSimpleName(), "already logged in");
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), Objects.requireNonNull(e.getMessage()));
        }
    }

    private Boolean evaluateUsernameFormat(String email) {
        return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }
}
