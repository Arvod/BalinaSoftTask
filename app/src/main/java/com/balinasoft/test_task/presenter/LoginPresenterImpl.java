package com.balinasoft.test_task.presenter;

import android.util.Log;
import android.widget.Toast;

import com.balinasoft.test_task.api.Api;
import com.balinasoft.test_task.model.RegistrationBody;
import com.balinasoft.test_task.model.RegistrationResponse;
import com.balinasoft.test_task.platform.App;
import com.balinasoft.test_task.view.LoginView;
import com.balinasoft.test_task.domain.login.LoginInteractor;
import com.balinasoft.test_task.domain.login.LoginInteractorImpl;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor interactor;

    private String email;
    private String password;


    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void validateLoginAndPassword(String login, String password) {
        if (interactor != null) {
            interactor.validateLoginAndPassword(login, password);
        }
    }

    @Override
    public void tryToLogin(final String login, final String password) {
        this.email = login;
        this.password = password;
        if (interactor != null) {
            interactor.tryToLogin(login, password);
        }
    }

    @Override
    public void onLoginError(String error) {
        if (loginView != null) {
            loginView.setLoginError(error);
            if (error != null) {
                loginView.setLoginFocused();
            }
        }
    }

    @Override
    public void onPasswordError(String error) {
        if (loginView != null) {
            loginView.setPasswordError(error);
            if (error != null) {
                loginView.setPasswordFocused();
            }
        }
    }

    @Override
    public void onLoginSuccess() {
        if (loginView != null) {
            loginView.hideKeyboard();


            RegistrationBody body = new
                    RegistrationBody(email, password);
            Api api = App.getRetrofit().create(Api.class);
            Call<RegistrationResponse> call = api.registerUser(body);
            call.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Response<RegistrationResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Toast.makeText(App.getContext(), "Сongratulations! You are signup", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            if (String.valueOf(response.errorBody().string()).contains("security.signup.login-already-use")) {
                                Toast.makeText(App.getContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                                loginView.setLoginFocused();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(App.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onLoginFail() {
        email = "";
        password = "";
        if (loginView != null) {
            loginView.setFocusedView();
            loginView.showKeyboard();
        }
    }

}
