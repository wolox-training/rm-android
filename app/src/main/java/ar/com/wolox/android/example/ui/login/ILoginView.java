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

    void onPasswordAlreadyStored(@NonNull String passwordStored);

    void goToHomePageScreen();

    void goToSignUpScreen();

    void goToSignInGoogleScreen();

    void showProgressBar();

    void hideProgressBar();
}
