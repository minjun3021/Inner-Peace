package com.kmj.innerpeace.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kmj.innerpeace.Data.RegisterData;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.retrofit.NetworkHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText id, pw, name, phone;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setup();
    }

    void setup() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#121319"));

        id = findViewById(R.id.register_id);
        pw = findViewById(R.id.register_pw);
        name = findViewById(R.id.register_name);

        phone = findViewById(R.id.register_phonenumber);
        register = findViewById(R.id.register_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().replace(" ", "").equals("")) {
                    if (!pw.getText().toString().replace(" ", "").equals("")) {
                        if (!name.getText().toString().replace(" ", "").equals("")) {

                            if (!phone.getText().toString().replace(" ", "").equals("")) {
                                //TODO retrofit
                                tryRegister();
                            } else {
                                Toast.makeText(RegisterActivity.this, "phone number를 입력하세요.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "name를 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "pw를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "id를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    tryRegister();
                    return true;
                }
                return false;
            }
        });
    }

    void tryRegister() {
        NetworkHelper.getInstance().register(id.getText().toString(),pw.getText().toString(),name.getText().toString(),phone.getText().toString()).enqueue(new Callback<RegisterData>() {
            @Override
            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                if(response.isSuccessful() && response!=null){
                    Toast.makeText(RegisterActivity.this, name.getText().toString()+"님 회원가입 성공", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                    Logger.e(response.body().getMessage());
                }
                else if(response.isSuccessful()){

                }
                else{
                    Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterData> call, Throwable t) {

            }
        });
    }
}
