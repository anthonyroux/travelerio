package com.example.evan.travelerio;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText userEditText, passwordEditText;
    private ConstraintLayout mProgressLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
            Log.d(TAG, "onCreate: version 19 or less");
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        switch (userStateDetails.getUserState()){
                            case SIGNED_IN:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d(TAG, "LoginActivity: Currently Signed In, Moving to MainActivity");
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                break;
                            case SIGNED_OUT:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d(TAG, "LoginActivity: Currently Signed Out");
                                    }
                                });
                                break;
                            default:
                                AWSMobileClient.getInstance().signOut();
                                break;
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );

        //Textview drawables must be inflated programmitcally otherwise does not work < API 19

        mProgressLayout = (ConstraintLayout) findViewById(R.id.login_progress_layout);

        TextView appName = findViewById(R.id.appname);
        Drawable appNameIcon = AppCompatResources.getDrawable(this, R.drawable.travelorio_icon);
        appName.setCompoundDrawablesWithIntrinsicBounds(null, null, appNameIcon, null);

        userEditText = findViewById(R.id.edittext_username);
        Drawable profileIcon =  AppCompatResources.getDrawable(this, R.drawable.icon_profile);
        userEditText.setCompoundDrawablesWithIntrinsicBounds(profileIcon, null, null, null);

        passwordEditText = findViewById(R.id.edittext_password);
        Drawable passwordIcon =  AppCompatResources.getDrawable(this, R.drawable.icon_password);
        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(passwordIcon, null, null, null);



        final Button signIn = (Button) findViewById(R.id.login_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



    }

    private void signIn(){

        String username = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //Checks if username and password fields are filled out
        if(checkIfStringNull(username) && checkIfStringNull(password)){
            Toast.makeText(LoginActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
        }else{
            MethodMisc.closeKeyboard(this);
            userEditText.clearFocus();
            passwordEditText.clearFocus();
            //disables all views
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mProgressLayout.setVisibility(View.VISIBLE);

            AWSMobileClient.getInstance().signIn(username, password, null, new Callback<SignInResult>() {
                @Override
                public void onResult(final SignInResult signInResult) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                            switch (signInResult.getSignInState()) {
                                case DONE:
                                    Log.d(TAG, "run: Signed in");
                                    mProgressLayout.setVisibility(View.GONE);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                case SMS_MFA:
                                    mProgressLayout.setVisibility(View.GONE);
                                    Log.d(TAG, "run: case SMS_MFA error");
                                    break;
                                case NEW_PASSWORD_REQUIRED:
                                    mProgressLayout.setVisibility(View.GONE);
                                    Log.d(TAG, "run: case NEW_PASSWORD_REQUIRED error");
                                    break;
                                default:
                                    mProgressLayout.setVisibility(View.GONE);
                                    //re-enables buttons
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Log.d(TAG, "run: Unsupported sign-in confirmation: " + signInResult.getSignInState());
                                    break;
                            }
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "Sign-in error", e);

                    LoginActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            mProgressLayout.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });
        }

    }

    private boolean checkIfStringNull(String string) {
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }

    }

}
