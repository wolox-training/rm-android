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
import butterknife.BindView;
import butterknife.ButterKnife;

import static ar.com.wolox.android.example.utils.Extras.ExternalLinks.TERMSCONDITIONS;

/**
 *
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.vLoginButton) Button loginButton;
    @BindView(R.id.vLoginUsername) EditText username;
    @BindView(R.id.vLoginPassword) EditText password;

    private Button signupButton;

    private TextView termsConditions;

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
        Intent intent = new Intent(getActivity(), HomePageActivity.class);
        startActivity(intent);
    }

}
