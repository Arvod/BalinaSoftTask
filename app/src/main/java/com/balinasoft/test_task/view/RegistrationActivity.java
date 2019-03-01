package com.balinasoft.test_task.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.test_task.R;
import com.balinasoft.test_task.platform.ResourceSup;
import com.balinasoft.test_task.presenter.LoginPresenter;
import com.balinasoft.test_task.presenter.LoginPresenterImpl;

public class RegistrationActivity extends AppCompatActivity implements
        LoginView,
        TextWatcher,
        View.OnClickListener,
        View.OnTouchListener,
        TextView.OnEditorActionListener {

    private LoginPresenter presenter;

    private TextInputEditText passwordEditText;
    private AutoCompleteTextView emailEditText;
    private TextInputLayout emailContainer, passwordContainer;
    private Button signInButton;
    private View viewToGetFocus; // View getting focus on login / password errors.
    private ImageView logo;
    private Typeface tfMedium, tfRoman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        presenter = new LoginPresenterImpl(this);
    }

    public void initComponents() {
        emailContainer = findViewById(R.id.emailContainer);
        passwordContainer = findViewById(R.id.passwordContainer);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);

        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
        logo = findViewById(R.id.logo);
        passwordEditText.setOnEditorActionListener(this);

        tfMedium = Typeface.createFromAsset(getAssets(), "fonts/helvetica_neue_cyr_medium.ttf");
        tfRoman = Typeface.createFromAsset(getAssets(), "fonts/helvetica_neue_cyr_roman.ttf");

        EmailAdapter adapter = new EmailAdapter(this, android.R.layout.simple_list_item_1, ResourceSup.getDomens());
        emailEditText.setAdapter(adapter);

        emailContainer.setTypeface(tfRoman);
        passwordContainer.setTypeface(tfRoman);
        signInButton.setTypeface(tfMedium);
        emailEditText.setTypeface(tfMedium);
        passwordEditText.setTypeface(tfMedium);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Assigning TextWatcher listener here to prevent early calls.
        emailEditText.addTextChangedListener(this);
        passwordEditText.addTextChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Preventing TextWatcher calls.
        emailEditText.removeTextChangedListener(this);
        passwordEditText.removeTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        String login = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        presenter.tryToLogin(login, password);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        return true;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == 100 || actionId == EditorInfo.IME_ACTION_DONE) {
            String login = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            presenter.tryToLogin(login, password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setLoginError(String error) {
        emailContainer.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        passwordContainer.setError(error);
    }

    @Override
    public void setLoginFocused() {
        viewToGetFocus = emailEditText;
    }

    @Override
    public void setPasswordFocused() {
        viewToGetFocus = passwordEditText;
    }

    @Override
    public void setFocusedView() {
        if (viewToGetFocus != null) {
            viewToGetFocus.requestFocus();
        }
    }

    @Override
    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) RegistrationActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(RegistrationActivity.this.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) RegistrationActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(RegistrationActivity.this.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
            imm.hideSoftInputFromWindow(RegistrationActivity.this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String login = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        presenter.validateLoginAndPassword(login, password);
    }
}
