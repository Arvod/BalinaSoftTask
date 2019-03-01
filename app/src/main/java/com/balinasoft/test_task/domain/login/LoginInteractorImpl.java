package com.balinasoft.test_task.domain.login;


import com.balinasoft.test_task.R;
import com.balinasoft.test_task.platform.ResourceSup;

public class LoginInteractorImpl implements LoginInteractor, Runnable {

    private static final String REGEX_EMPTY_STRING = "^$";
    private static final String REGEX_TOO_SHORT_PASSWORD = "^.{8,}$";
    private static final String REGEX_TOO_LONG_PASSWORD = "^.{15,}$";

    private OnLoginFinishedListener listener;

    public LoginInteractorImpl(OnLoginFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean validateLoginAndPassword(String email, String password) {

        boolean noLoginErrors = true;
        boolean noPasswordErrors = true;

        if (password.matches(REGEX_EMPTY_STRING)) {
            listener.onPasswordError(ResourceSup.getString(R.string.empty_password_error_message_label));
            noPasswordErrors = false;
        } else if (!password.matches(REGEX_TOO_SHORT_PASSWORD)) {
            listener.onPasswordError(ResourceSup.getString(R.string.short_password_error_message_label));
            noPasswordErrors = false;
        } else if (password.matches(REGEX_TOO_LONG_PASSWORD)) {
            listener.onPasswordError(ResourceSup.getString(R.string.long_password_error_message_label));
        } else {
            listener.onPasswordError(null);
        }
        if (email.matches(REGEX_EMPTY_STRING)) {
            listener.onLoginError(ResourceSup.getString(R.string.mailIsEmpty));
            noLoginErrors = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            listener.onLoginError(ResourceSup.getString(R.string.incorrectEmail));
            noLoginErrors = false;
        } else {
            listener.onLoginError(null);
        }
        return noLoginErrors && noPasswordErrors;

    }

    @Override
    public void tryToLogin(String login, String password) {
        if (validateLoginAndPassword(login, password)) {
            listener.onLoginSuccess();
        } else {
            listener.onLoginFail();
        }
    }

    @Override
    public void run() {
        listener.onLoginSuccess();
    }
}
