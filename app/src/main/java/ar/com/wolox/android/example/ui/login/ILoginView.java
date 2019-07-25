package ar.com.wolox.android.example.ui.login;

import androidx.annotation.NonNull;

/**
 *
 */
public interface ILoginView {

    void onEmptyUsername();

    void onEmptyPassword();

    void onEmptyUsernameAndPassword();

    void onWrongUsernameFormat();

    void goToTermsConditionsScreen();

    void onUsernameAlreadyStored(@NonNull String usernameStored);

    void goToHomePageScreen();

    void goToSignUpScreen();

    void showProgressBar();

    void hideProgressBar();
}
