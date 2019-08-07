package ar.com.wolox.android.example.ui.root;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import ar.com.wolox.android.R;
import ar.com.wolox.android.example.model.User;
import ar.com.wolox.android.example.network.LoginService;
import ar.com.wolox.android.example.ui.home.HomePageActivity;
import ar.com.wolox.android.example.ui.login.LoginActivity;
import ar.com.wolox.android.example.utils.NetworkUtils;
import ar.com.wolox.android.example.utils.UserSession;
import ar.com.wolox.wolmo.core.activity.WolmoActivity;
import ar.com.wolox.wolmo.core.util.ToastFactory;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ar.com.wolox.android.example.utils.Extras.UserLogin.LOGGED_APP;
import static ar.com.wolox.android.example.utils.Extras.UserLogin.LOGGED_GOOGLE;
import static ar.com.wolox.android.example.utils.Extras.UserLogin.RC_GOOGLE_SIGN_IN;

/**
 *
 */
public class RootActivity extends WolmoActivity {

    @Inject UserSession mUserSession;
    @Inject RetrofitServices mRetrofitServices;
    @Inject ToastFactory mToastFactory;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected int layout() {
        return R.layout.activity_base;
    }

    @Override
    protected void init() {
        redirectToScreen();
    }

    private void redirectToScreen() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            if (mUserSession.getLoggedType() != null) {
                switch (mUserSession.getLoggedType()) {
                    case LOGGED_APP:
                        if (mUserSession.getUsername() != null && mUserSession.getPassword() != null) {
                            validateUser(mUserSession.getUsername(), mUserSession.getPassword());
                        } else {
                            goToLoginScreen();
                        }
                        break;
                    case LOGGED_GOOGLE:
                        if (mUserSession.getUsername() != null && mUserSession.getUserId() != null &&
                                GoogleSignIn.getLastSignedInAccount(this) != null) {
                            validateUserByGoogle();
                        } else {
                            goToLoginScreen();
                        }
                        break;
                    default:
                        goToLoginScreen();
                }
            } else {
                goToLoginScreen();
            }
        } else {
            mToastFactory.show(R.string.network_error_message);
            goToLoginScreen();
        }
    }

    private void validateUser(@NonNull String username, @NonNull String password) {
        mRetrofitServices.getService(LoginService.class).getUserByCredentials(username, password).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                assert response.body() != null;
                if (response.body().size() > 0) {
                    goToHomePageScreen();
                } else {
                    cleanCredentials();
                    goToLoginScreen();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                mToastFactory.show(R.string.login_error_service_message);
                Log.e(getClass().getSimpleName(), Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void validateGoogleUser(@NonNull String username) {
        mRetrofitServices.getService(LoginService.class).getUserByMail(username).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                assert response.body() != null;
                if (response.body().size() > 0) {
                    goToHomePageScreen();
                } else {
                    cleanCredentials();
                    goToLoginScreen();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                mToastFactory.show(R.string.login_error_service_message);
                Log.e(getClass().getSimpleName(), Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void validateUserByGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    private void goToHomePageScreen() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void cleanCredentials() {
        mUserSession.setUsername(null);
        mUserSession.setPassword(null);
        mUserSession.setUserId(null);
        mUserSession.setLoggedType(null);
    }

    /**
     *
     * @param completedTask Completed task
     */
    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                if (account.getEmail() != null) {
                    validateGoogleUser(account.getEmail());
                } else {
                    mToastFactory.show(R.string.login_google_not_completed_message);
                    googleSignInClient.signOut();
                    goToLoginScreen();
                }
            } else {
                mToastFactory.show(R.string.login_google_not_completed_error_message);
                googleSignInClient.signOut();
                goToLoginScreen();
            }
        } catch (ApiException e) {
            mToastFactory.show(R.string.login_google_not_completed_error_message);
            googleSignInClient.signOut();
            goToLoginScreen();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_GOOGLE_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            } else {
                goToLoginScreen();
            }
        }
    }

}
