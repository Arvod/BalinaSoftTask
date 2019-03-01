package com.balinasoft.test_task.presenter;

public interface LoginPresenter {

    void validateLoginAndPassword(String login, String password);

    void tryToLogin(String login, String password);

}

