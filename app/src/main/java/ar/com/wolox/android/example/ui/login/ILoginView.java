package ar.com.wolox.android.example.ui.login;

/**
 *
 */
public interface ILoginView {

    void onEmptyUsername();

    void onEmptyPassword();

    void onWrongUsernameFormat();

    void goToHomePageScreen();

    void goToSignUpScreen();

    void goToTermsConditionsScreen();
}
