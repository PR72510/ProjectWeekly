package com.example.projectweekly.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectweekly.BroadcastReciever.NetworkStateReciever;
import com.example.projectweekly.DataManager.DataManger;
import com.example.projectweekly.Interfaces.LoginRegisterResponseListener;
import com.example.projectweekly.Interfaces.NetworkStateChangeListener;
import com.example.projectweekly.Model.TokenResponseModel;
import com.example.projectweekly.Model.UserCredentialModel;
import com.example.projectweekly.R;


public class MainActivity extends AppCompatActivity implements LoginRegisterResponseListener, NetworkStateChangeListener {

    NetworkStateReciever networkStateReciever;
    Button btnLogin, btnRegister;
    EditText etEmail, etPassword;
    SharedPreferences sharedPreferences;
    public static final String MY_PREFS = "myPref";
    Boolean firstTime;
    String email, password;
    DataManger dataManger = DataManger.getInstance();
    TokenResponseModel token;
    Boolean isConnected;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("firstTime", true);
        if (firstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            firstTime = false;
            editor.putBoolean("firstTime", firstTime);
            editor.apply();
            setContentView(R.layout.splash_screen);

            new CountDownTimer(3000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    load();
                }
            }.start();
        } else {
            load();
        }

    }


    private void load() {
        setContentView(R.layout.activity_main);
        dataManger.setInstance(this);
        btnLogin = findViewById(R.id.btn_Login);
        btnRegister = findViewById(R.id.btn_Register);
        etEmail = findViewById(R.id.et_Email);
        etPassword = findViewById(R.id.et_Password);
        constraintLayout = findViewById(R.id.contain);
        networkStateReciever = new NetworkStateReciever();
        networkStateReciever.setOnNetworkStateChanged(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(etEmail.getText()).trim();
                password = String.valueOf(etPassword.getText()).trim();
                login(email, password);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(etEmail.getText()).trim();
                password = String.valueOf(etPassword.getText()).trim();
                register(email, password);
            }
        });
    }


    private void register(String email, String password) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Email and Password field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        else{
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString("EMAIL", email).putString("PASSWORD", password);
            e.apply();
            UserCredentialModel user = new UserCredentialModel(email, password);
            dataManger.registerUser(user);
            etEmail.setText("");
            etPassword.setText("");
        }
    }

    private void login(String email, String password) {
        if(email.equals(sharedPreferences.getString("EMAIL","")) && password.equals(sharedPreferences.getString("PASSWORD",""))){
            UserCredentialModel user = new UserCredentialModel(email, password);
            dataManger.loginUser(user);

            Intent intent = new Intent(MainActivity.this, Second_Activity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "User not registered", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        firstTime = true;
        editor.putBoolean("firstTime", firstTime);
        editor.apply();
        finish();
    }


    @Override
    public void tokenRegisterResonseListener(TokenResponseModel token) {
        this.token = token;
        Toast.makeText(this, "User Registered. Token: " +token.getToken(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenLoginResonseListener(TokenResponseModel token) {
        this.token = token;
        Toast.makeText(this, "User Logged In. Token: " +token.getToken(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReciever, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkStateReciever);
    }

    @Override
    public void isNetworkConnected(boolean network_state) {
        isConnected = network_state;
        showSnackBar(isConnected);
    }

    private void showSnackBar(Boolean isConnected) {
        Snackbar snackbar;
        if(isConnected){
            snackbar = Snackbar.make(constraintLayout, "Connected to Internet", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(Color.argb(255, 0, 150, 100));

        }
        else{
            snackbar = Snackbar.make(constraintLayout, "Cannot connect to Internet", Snackbar.LENGTH_INDEFINITE);
            snackbar.getView().setBackgroundColor(Color.argb(255, 178, 34, 34));
        }
        snackbar.show();
    }
}
