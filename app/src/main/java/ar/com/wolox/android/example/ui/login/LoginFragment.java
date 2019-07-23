package ar.com.wolox.android.example.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import ar.com.wolox.android.R;
import ar.com.wolox.android.example.ui.home.HomePageActivity;
import ar.com.wolox.android.example.ui.signup.SignupActivity;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;

import static ar.com.wolox.android.example.utils.Extras.ExternalLinks.TERMSCONDITIONS;

/**
 *
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    private EditText username;

    private EditText password;

    private Button loginButton;

    private Button signupButton;

    private TextView termsConditions;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {
        this.username = Objects.requireNonNull(getView()).findViewById(R.id.vLoginUsername);
        this.password = Objects.requireNonNull(getView()).findViewById(R.id.vLoginPassword);
        this.loginButton = Objects.requireNonNull(getView()).findViewById(R.id.vLoginButton);
        this.signupButton = Objects.requireNonNull(getView()).findViewById(R.id.vSignupButton);
        this.termsConditions = Objects.requireNonNull(getView()).findViewById(R.id.vLoginTermsConditions);
    }

    @Override
    public void setListeners() {
        loginButton.setOnClickListener(view -> getPresenter().startLogin(username.getText().toString(), password.getText().toString()));
        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SignupActivity.class);
            startActivity(intent);
        });
        termsConditions.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TERMSCONDITIONS));
            startActivity(intent);
        });
    }

    @Override
    public void onEmptyForm() {
        if (username.getText().toString().isEmpty()) {
            username.setError(getResources().getString(R.string.login_required_field));
        }

        if (password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.login_required_field));
        }
    }

    @Override
    public void onWrongUsernameFormat() {
        username.setError(getResources().getString(R.string.login_wrong_username_format));
    }

    @Override
    public void onUserLoggedIn() {
        Intent intent = new Intent(getActivity(), HomePageActivity.class);
        startActivity(intent);
    }
}
