package ar.com.wolox.android.example.ui.login;

/**
 *
 */
public interface ILoginView {

    void onEmptyUsername();

    void onEmptyPassword();

    void onEmptyUsernameAndPassword();

    void onWrongUsernameFormat();

    void onUsernameAlreadyStored(String usernameStored);

    void goToHomePageScreen();

    void goToSignUpScreen();

    void goToTermsConditionsScreen();
}
