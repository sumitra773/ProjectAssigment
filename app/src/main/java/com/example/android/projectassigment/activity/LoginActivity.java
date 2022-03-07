package com.example.android.projectassigment.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.projectassigment.R;
import com.example.android.projectassigment.model.ApiResponse;
import com.example.android.projectassigment.model.LoginRequest;
import com.example.android.projectassigment.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtMobileNumber, edtPassword;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtMobileNumber = findViewById(R.id.et_mobile_number);
        edtPassword = findViewById(R.id.et_password);


        findViewById(R.id.btn_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edtMobileNumber.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "MobileNumber / password Required", Toast.LENGTH_SHORT).show();

                } else {
                    doLogin();
                }

            }

        });

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Processing...");

        findViewById(R.id.tv_change_mobile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordView();
            }
        });

    }


    public void doLogin() {
        mDialog.show();
        LoginRequest loginRequest = new LoginRequest(edtMobileNumber.getText().toString(), edtPassword.getText().toString());
        loginRequest.setMobile(edtMobileNumber.getText().toString());
        loginRequest.setPassword(edtPassword.getText().toString());
        Call<ApiResponse> apiResponseCall = RetrofitClient.getInstance().getApi().userLogin(loginRequest);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if(response.isSuccessful()){

                    if(response.body() != null && response.body().getStatus()) {
                        showOtpView();
                        mDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showOtpView() {
        findViewById(R.id.group_otp).setVisibility(View.VISIBLE);
        findViewById(R.id.group_password).setVisibility(View.GONE);
    }

    private void showPasswordView() {
        findViewById(R.id.group_otp).setVisibility(View.GONE);
        findViewById(R.id.group_password).setVisibility(View.VISIBLE);
    }

}