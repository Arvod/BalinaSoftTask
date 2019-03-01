package com.balinasoft.test_task.view;

public interface LoginView {

    void setLoginError(String error);

    void setPasswordError(String error);

    void setLoginFocused();

    void setPasswordFocused();

    void setFocusedView();

    void showKeyboard();

    void hideKeyboard();

}