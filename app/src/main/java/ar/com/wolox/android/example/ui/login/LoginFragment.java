package ar.com.wolox.android.example.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import ar.com.wolox.android.R;
import ar.com.wolox.android.example.ui.home.HomePageActivity;
import ar.com.wolox.android.example.ui.signup.SignUpActivity;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static ar.com.wolox.android.example.BaseConfiguration.TERMS_CONDITIONS_URL;
import static ar.com.wolox.android.example.utils.Extras.UserLogin.RC_GOOGLE_SIGN_IN;

/**
 *
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.vLoginButton) Button loginButton;
    @BindView(R.id.vSignupButton) Button signUpButton;
    @BindView(R.id.vGoogleSignIn) SignInButton loginGoogleButton;
    @BindView(R.id.vLoginUsername) EditText username;
    @BindView(R.id.vLoginPassword) EditText password;
    @BindView(R.id.vLoginTermsConditions) TextView termsConditions;
    @BindView(R.id.vLoginProgressBar) ProgressBar progressBar;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {
        ButterKnife.bind(this, Objects.requireNonNull(getActivity()));
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Override
    public void setListeners() {
        loginButton.setOnClickListener(view ->
                getPresenter().onLoginButtonClicked(username.getText().toString(), password.getText().toString())
        );
        loginGoogleButton.setOnClickListener(view -> getPresenter().onGoogleSignInButtonClicked());
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
    public void onUsernameAlreadyStored(@NonNull String usernameStored) {
        if (username != null) {
            username.setText(usernameStored);
        }
    }

    @Override
    public void onPasswordAlreadyStored(@NonNull String passwordStored) {
        if (password != null) {
            password.setText(passwordStored);
        }
    }

    @Override
    public void goToHomePageScreen() {
        Intent intent = new Intent(getActivity(), HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void goToSignUpScreen() {
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void goToSignInGoogleScreen() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void goToTermsConditionsScreen() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_CONDITIONS_URL));
        startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void logOutGoogle() {
        mGoogleSignInClient.signOut();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            getPresenter().onGoogleSingedIn(task);
        }
    }
}
