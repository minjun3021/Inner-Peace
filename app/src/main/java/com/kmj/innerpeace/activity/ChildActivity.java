package com.kmj.innerpeace.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kmj.innerpeace.Data.Profile;
import com.kmj.innerpeace.Data.RegisterData;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.retrofit.NetworkHelper;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildActivity extends AppCompatActivity {
    EditText id, pw;
    Button register;
    String userToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        id = findViewById(R.id.childlogin_id);
        pw = findViewById(R.id.childlogin_pw);
        register = findViewById(R.id.childlogin_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().replace(" ", "").equals("")) {
                    if (!pw.getText().toString().replace(" ", "").equals("")) {
                        NetworkHelper.getInstance().login(id.getText().toString(),pw.getText().toString()).enqueue(new Callback<RegisterData>() {
                            @Override
                            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                                if(response.isSuccessful()&&response!=null){
                                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("ChildToken",response.body().getData());
                                    editor.commit();

                                    userToken=response.body().getData();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            NetworkHelper.getInstance().getMyProfile(userToken).enqueue(new Callback<Profile>() {
                                                @Override
                                                public void onResponse(Call<Profile> call, Response<Profile> response) {
                                                    if(response.isSuccessful()&& response!=null){

                                                        Logger.e(response.body().getData().getName());
                                                        Logger.e(response.body().getData().getImgPath());
                                                        MainActivity.name=response.body().getData().getName();
                                                        MainActivity.email=response.body().getData().getEmail();
                                                        MainActivity.phone=response.body().getData().getPhone();
                                                        if(!response.body().getData().getImgPath().equals("")){
                                                            MainActivity.imgPath="http://34.84.240.128:8000/"+response.body().getData().getImgPath();

                                                        }
                                                        else{
                                                            MainActivity.imgPath="";
                                                        }


                                                    }else{

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Profile> call, Throwable t) {

                                                }
                                            });
                                            Intent intent = new Intent(ChildActivity.this, MainActivity.class);

                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);
                                }
                                else{
                                    Toast.makeText(ChildActivity.this, "자식 계정을 확인해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterData> call, Throwable t) {

                            }
                        });
                    } else {
                        Toast.makeText(ChildActivity.this, "자녀 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChildActivity.this, "자녀의 id를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

