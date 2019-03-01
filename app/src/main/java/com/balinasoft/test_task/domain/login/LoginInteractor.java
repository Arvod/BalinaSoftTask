package com.balinasoft.test_task.domain.login;

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onLoginError(String error);

        void onPasswordError(String error);

        void onLoginSuccess();

        void onLoginFail();
    }

    boolean validateLoginAndPassword(String login, String password);

    void tryToLogin(String login, String password);

}
