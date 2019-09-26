package com.kmj.innerpeace.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.kmj.innerpeace.Data.Profile;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.retrofit.NetworkHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    String userToken;
    String childToken;
    int isParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences pref =getSharedPreferences("pref", MODE_PRIVATE);
        isParent=pref.getInt("isParent",0);
        userToken=pref.getString("userToken", "");
        childToken=pref.getString("ChildToken","");
        setup();

        if(isParent==1 &&childToken.equals("")){
            Logger.e("1");
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(isParent==1 && !childToken.equals("")){
            Logger.e("2");
            userToken=childToken;
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
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
        else if (pref.getString("userToken","").equals("")){
            Logger.e("3");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }

        else{
            Logger.e("4");
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
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }
    void setup() {
        Window window = getWindow();
        Logger.addLogAdapter(new AndroidLogAdapter());
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#121319"));
    }
}
