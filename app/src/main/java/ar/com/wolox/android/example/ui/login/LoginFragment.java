package ar.com.wolox.android.example.ui.login;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;

/**
 *
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    private EditText username;

    private EditText password;

    private Button loginButton;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {
        this.username = Objects.requireNonNull(getView()).findViewById(R.id.vLoginUsername);
        this.password = Objects.requireNonNull(getView()).findViewById(R.id.vLoginPassword);
        this.loginButton = Objects.requireNonNull(getView()).findViewById(R.id.vLoginButton);
    }

    @Override
    public void setListeners() {
        loginButton.setOnClickListener(view -> getPresenter().startLogin(username.getText().toString(), password.getText().toString()));
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
        Log.d(getClass().getSimpleName(), "Logged In");
    }
}
