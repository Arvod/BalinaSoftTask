package com.balinasoft.test_task;

import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balinasoft.test_task.model.RegistrationBody;
import com.balinasoft.test_task.model.RegistrationResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private AutoCompleteTextView mAutoCompleteTextView;
    private Button registrationButton;
    private ImageView logo;
    private Retrofit retrofit;
    private Gson gson = new GsonBuilder().create();
    private Api api;
    private LinearLayout rootView;


    private boolean invisable = true;
    private Typeface tfMedium, tfRoman;
    private Animation fade;
    private String message;
    private Animation scroll;
    static final String URL = "http://junior.balinasoft.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initAnimation();
        initRetrofit();

        email.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invisable) {
                    animate();
                }else {
                    email.setError(null);
                    validatePassword();
                }
            }
        });

        password.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invisable) {
                    animate();
                }else {
                    password.setError(null);
                    validateEmailText();
                }
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmailText() && validatePassword()){
                    RegistrationBody body = new
                            RegistrationBody(email.getEditText().getText().toString().trim(),
                            password.getEditText().getText().toString().trim());

                    Call<RegistrationResponse> call = api.registerUser(body);
                    call.enqueue(new Callback<RegistrationResponse>() {
                        @Override
                        public void onResponse(Response<RegistrationResponse> response, Retrofit retrofit) {
                            if(response.isSuccess()) {
                                Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Defeat", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });

                }
            }
        });


    }

    public void animate(){
        rootView.startAnimation(scroll);
        logo.startAnimation(fade);
        invisable = false;
    }

    public static boolean isCoorrectEmail(String testString) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9][a-zA-Z0-9_\\\\-\\\\.\\\\+]*)?(|\\\\'|)+@(\\[?)[a-zA-Z0-9\\-\\.]+\\.(?:|ru|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)$");
        Matcher m = p.matcher(testString);
        return m.matches();
    }

    private boolean validateEmailText() {
        password.setError(null);
        String emailInput = email.getEditText().getText().toString().trim();
        String domainIsNotCorrectly = getString(R.string.domainIsNotCorrectly);
        String mailIsEmpty = getString(R.string.mailIsEmpty);
        if (emailInput.isEmpty()) {
            email.setError(mailIsEmpty);
            return false;
        }
        if (!isCoorrectEmail(emailInput)) {
            email.setError(domainIsNotCorrectly);
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        email.setError(null);
        String passwordInput = password.getEditText().getText().toString().trim();
        String passwordIsEmpty = getString(R.string.passwordIsEmpty);
        String passwordIsLong = getString(R.string.passwordIsLong);
        String passwordIsShort = getString(R.string.passwordIsShort);
        if (passwordInput.isEmpty()) {
            email.setError(passwordIsEmpty);
            return false;
        }else if (passwordInput.length() > 8){
            password.setError(passwordIsLong);
            return false;
        }else if (passwordInput.length() < 3){
            password.setError(passwordIsShort);
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public void initComponents(){
        email = (TextInputLayout) findViewById(R.id.text_input_email);
        password = (TextInputLayout) findViewById(R.id.text_input_password);
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        registrationButton = (Button) findViewById(R.id.registration_button);
        logo = (ImageView) findViewById(R.id.logo);
        rootView = (LinearLayout) findViewById(R.id.rootView);

        tfMedium = Typeface.createFromAsset(getAssets(), "fonts/helvetica_neue_cyr_medium.ttf");
        tfRoman = Typeface.createFromAsset(getAssets(), "fonts/helvetica_neue_cyr_roman.ttf");

        ArrayList<String> domens = new ArrayList<String>();
        domens.add("@gmail.com");
        domens.add("@gmail.ru");
        domens.add("@hotmail.com");
        domens.add("@yahoo.com");
        domens.add("@outlook.com");
        domens.add("@mail.ru");
        domens.add("@rambler.ru");
        domens.add("@inbox.ru");
        EmailAdapter adapter = new EmailAdapter(this,android.R.layout.simple_list_item_1,domens);
        mAutoCompleteTextView.setAdapter(adapter);

        email.setTypeface(tfMedium);
        password.setTypeface(tfMedium);
        registrationButton.setTypeface(tfMedium);
    }

    public void initAnimation(){
        fade = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.fade);
        scroll = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.scroll);

        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setImageAlpha(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        scroll.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                rootView.setTranslationY(-150);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();
        api = retrofit.create(Api.class);
    }
}
