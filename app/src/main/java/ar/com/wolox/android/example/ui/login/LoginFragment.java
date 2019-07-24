package ar.com.wolox.android.example.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import ar.com.wolox.android.R;
import ar.com.wolox.android.example.ui.home.HomePageActivity;
import ar.com.wolox.android.example.ui.signup.SignUpActivity;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static ar.com.wolox.android.example.BaseConfiguration.TERMS_CONDITIONS_URL;

/**
 *
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.vLoginButton) Button loginButton;
    @BindView(R.id.vSignupButton) Button signUpButton;
    @BindView(R.id.vLoginUsername) EditText username;
    @BindView(R.id.vLoginPassword) EditText password;
    @BindView(R.id.vLoginTermsConditions) TextView termsConditions;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {
        ButterKnife.bind(this, Objects.requireNonNull(getActivity()));
    }

    @Override
    public void setListeners() {
        loginButton.setOnClickListener(view ->
                getPresenter().onLoginButtonClicked(username.getText().toString(), password.getText().toString())
        );
        signUpButton.setOnClickListener(view -> getPresenter().onSignUpButtonClicked());
        termsConditions.setOnClickListener(view -> getPresenter().onTermsConditionsButtonClicked());
    }

    @Override
    public void onEmptyUsername() {
        username.setError(getResources().getString(R.string.login_required_field));
    }

    @Override
    public void onEmptyPassword() {
        password.setError(getResources().getString(R.string.login_required_field));
    }

    @Override
    public void onEmptyUsernameAndPassword() {
        username.setError(getResources().getString(R.string.login_required_field));
        password.setError(null);
    }

    @Override
    public void onWrongUsernameFormat() {
        username.setError(getResources().getString(R.string.login_wrong_username_format));
    }

    @Override
    public void onUsernameAlreadyStored(String usernameStored) {
        if (username != null) {
            username.setText(usernameStored);
        }
    }

    @Override
    public void goToHomePageScreen() {
        Intent intent = new Intent(getActivity(), HomePageActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToSignUpScreen() {
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void goToTermsConditionsScreen() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_CONDITIONS_URL));
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().saveFormBeforeDestroy(username.getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().attachView(this);
    }
}
