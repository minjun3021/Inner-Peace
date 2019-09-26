package com.kmj.innerpeace.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kmj.innerpeace.Data.RegisterData;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.retrofit.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentregisterActivity extends AppCompatActivity {
    EditText id, pw;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentregister);
        id = findViewById(R.id.parentregister_id);
        pw = findViewById(R.id.parentregister_pw);
        register = findViewById(R.id.parentregister_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().replace(" ", "").equals("")) {
                    if (!pw.getText().toString().replace(" ", "").equals("")) {
                        NetworkHelper.getInstance().register(id.getText().toString(), pw.getText().toString(), " ", " ").enqueue(new Callback<RegisterData>() {
                            @Override
                            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                                if (response.isSuccessful() && response != null) {
                                    Toast.makeText(ParentregisterActivity.this, "부모 계정 회원가입성공", Toast.LENGTH_SHORT).show();
                                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("isParent", 1);
                                    editor.commit();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            finish();


                                        }
                                    }, 500);
                                } else {
                                    Toast.makeText(ParentregisterActivity.this, "다시시도하세요", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterData> call, Throwable t) {

                            }
                        });
                    } else {
                        Toast.makeText(ParentregisterActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParentregisterActivity.this, "id를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
