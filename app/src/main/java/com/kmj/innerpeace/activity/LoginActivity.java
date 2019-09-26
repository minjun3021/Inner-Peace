package com.kmj.innerpeace.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kmj.innerpeace.Data.RegisterData;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.retrofit.NetworkHelper;
import com.kmj.innerpeace.util.BackPressCloseHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText id, pw;
    Button login;
    TextView register;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    BackPressCloseHandler backPressCloseHandler;
    Switch aswitch;
    Boolean isParent=false;
    int isParentAccount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();


    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
        //super.onBackPressed();
    }

    void setup() {
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        isParentAccount=pref.getInt("isParent",0);
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#121319"));
        Logger.addLogAdapter(new AndroidLogAdapter());
        backPressCloseHandler = new BackPressCloseHandler(this);

        editor = pref.edit();
        aswitch=findViewById(R.id.login_switch);
        aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isParent=isChecked;
            }
        });
        id = findViewById(R.id.login_id);
        pw = findViewById(R.id.login_pw);
        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.login_register);
        id.setSelection(id.length());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().replace(" ", "").equals("")) {
                    if (!pw.getText().toString().replace(" ", "").equals("")) {
                        //TODO retrofit
                        tryLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, "pw를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "id를 입력하세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    pw.setSelection(pw.length());
                    return true;
                }
                return false;
            }
        });
        pw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    tryLogin();
                    return true;
                }
                return false;
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isParent){
                    Intent intent = new Intent(LoginActivity.this, ParentregisterActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    void tryLogin() {
        isParentAccount=pref.getInt("isParent",0);
        Logger.e(String.valueOf(isParentAccount));

        NetworkHelper.getInstance().login(id.getText().toString(),pw.getText().toString()).enqueue(new Callback<RegisterData>() {
            @Override
            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
               if (response.isSuccessful() && response!=null){
                   if(isParentAccount==1){
                       Intent intent=new Intent(LoginActivity.this,ChildActivity.class);
                       startActivity(intent);
                       finish();
                   }
                   else{
                       Logger.e(response.body().getData());
                       editor.putString("userToken",response.body().getData());
                       editor.commit();
                       Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                       startActivity(intent);
                       finish();
                   }

               }
               else{
                   Toast.makeText(LoginActivity.this, "Email 또는 Password가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<RegisterData> call, Throwable t) {

            }
        });
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
    }
}
