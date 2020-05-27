package com.wambuacooperations.travelblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout usernameEditTextLayout;
    TextInputLayout passwordEditTextLayout;
    Button loginButton;
    ProgressBar progressBar;
    BlogPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences=new BlogPreferences(this);
        if(preferences.isLoggedIn()){
            startMainActivity();
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        usernameEditTextLayout=findViewById(R.id.textUsernameInput);
        passwordEditTextLayout=findViewById(R.id.textPasswordInput);
        progressBar=findViewById(R.id.progressBar);
        loginButton=findViewById(R.id.loginButton);

    }



    public void loginClicked(View view){
        String username=usernameEditTextLayout.getEditText().getText().toString();
        String password=passwordEditTextLayout.getEditText().getText().toString();

        if (username.isEmpty()){
            usernameEditTextLayout.setError("Username must not be empty");
        }else if(password.isEmpty()){
            passwordEditTextLayout.setError("Password must not be empty");
        }else if(!(username.equals("admin"))||!(password.equals("admin"))){
            showErrorDialog();
        }else{
            performLogin();
        }

        usernameEditTextLayout.getEditText().addTextChangedListener(createTextWatcher(usernameEditTextLayout));
        passwordEditTextLayout.getEditText().addTextChangedListener(createTextWatcher(passwordEditTextLayout));
    }




    private TextWatcher createTextWatcher(final TextInputLayout passwordEditTextLayout){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditTextLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    }


    private void performLogin() {
        preferences.setLoggedIn(true);
        loginButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        usernameEditTextLayout.setEnabled(false);
        passwordEditTextLayout.setEnabled(false);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               startMainActivity();
                finish();

            }
        },2000);
    }


    private void startMainActivity() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);

    }


    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Username or password is not correct. Please try again")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


}
