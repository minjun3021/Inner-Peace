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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences prefs =getSharedPreferences("pref", MODE_PRIVATE);
        userToken=prefs.getString("userToken", "");
        setup();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        if (pref.getString("userToken","").equals("")){
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
