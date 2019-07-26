package ar.com.wolox.android.example.ui.root;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

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

/**
 *
 */
public class RootActivity extends WolmoActivity {

    @Inject UserSession mUserSession;
    @Inject RetrofitServices mRetrofitServices;
    @Inject ToastFactory mToastFactory;

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
            if (mUserSession.getUsername() != null &&
                    mUserSession.getPassword() != null) {
                validateUser(mUserSession.getUsername(), mUserSession.getPassword());
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
                    mUserSession.setUsername(null);
                    mUserSession.setPassword(null);
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

}
