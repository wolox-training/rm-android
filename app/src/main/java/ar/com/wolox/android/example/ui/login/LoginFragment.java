package ar.com.wolox.android.example.ui.login;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.vLoginButton) Button loginButton;
    @BindView(R.id.vLoginUsername) EditText username;
    @BindView(R.id.vLoginPassword) EditText password;

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
                getPresenter().loginButtonClicked(username.getText().toString(), password.getText().toString())
        );
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
    public void onWrongUsernameFormat() {
        username.setError(getResources().getString(R.string.login_wrong_username_format));
    }

    @Override
    public void onUserLoggedIn() {
        Log.d(getClass().getSimpleName(), "Logged In");
    }

}
