package com.kmj.innerpeace.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kmj.innerpeace.Data.Profile;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.retrofit.NetworkHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    String encodedString = "";
    Button info1, info2, info3, info4, info5, info6, info7, info8, info9, info10, info11, info12;
    Button stress1, stress2, stress3, stress4, stress5, stress6, stress7, stress8, stress9, stress10, stress11, stress12;
    Button cancel, ok;
    CircleImageView myProfile;
    ImageView back;
    TextView name, phone, email, mainname;
    int[] info=new int[13];
    int[] stress=new int[13];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setup();

    }

    void toBase64(File file) {
        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(file);
            Logger.e("good");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Logger.e("mygod");
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    void setup() {
        Window window = getWindow();
        Logger.addLogAdapter(new AndroidLogAdapter());
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#121319"));
        info1 = findViewById(R.id.info_1);
        info2 = findViewById(R.id.info_2);
        info3 = findViewById(R.id.info_3);
        info4 = findViewById(R.id.info_4);
        info5 = findViewById(R.id.info_5);
        info6 = findViewById(R.id.info_6);
        info7 = findViewById(R.id.info_7);
        info8 = findViewById(R.id.info_8);
        info9 = findViewById(R.id.info_9);
        info10 = findViewById(R.id.info_10);
        info11 = findViewById(R.id.info_11);
        info12 = findViewById(R.id.info_12);
        stress1 = findViewById(R.id.stress_1);
        stress2 = findViewById(R.id.stress_2);
        stress3 = findViewById(R.id.stress_3);
        stress4 = findViewById(R.id.stress_4);
        stress5 = findViewById(R.id.stress_5);
        stress6 = findViewById(R.id.stress_6);
        stress7 = findViewById(R.id.stress_7);
        stress8 = findViewById(R.id.stress_8);
        stress9 = findViewById(R.id.stress_9);
        stress10 = findViewById(R.id.stress_10);
        stress11 = findViewById(R.id.stress_11);
        stress12 = findViewById(R.id.stress_12);
        cancel = findViewById(R.id.setting_cancel);
        ok = findViewById(R.id.setting_ok);
        myProfile = findViewById(R.id.setting_profile);
        name = findViewById(R.id.setting_name);
        phone = findViewById(R.id.setting_number);
        email = findViewById(R.id.setting_email);
        mainname = findViewById(R.id.setting_text1);
        back = findViewById(R.id.setting_back);
        name.setText("이름 " + MainActivity.name);
        phone.setText("휴대폰 번호 " + MainActivity.phone);
        email.setText("이메일 " + MainActivity.email);

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TedImagePicker.with(SettingActivity.this).start(new OnSelectedListener() {
                    @Override
                    public void onSelected(Uri uri) {
                        Glide.with(SettingActivity.this)
                                .load(uri)
                                .placeholder(R.drawable.ic_profile)
                                .fitCenter()
                                .into(myProfile);
                        Logger.e(uri.toString());
                        File file = new File(uri.getPath());
                        toBase64(file);


                    }
                });
            }
        });

        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[1]==1){
                    info[1]=0;
                    info1.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info1.setBackgroundResource(R.drawable.button_soso);
                        info[1]=1;
                    }
                }

            }
        });
        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[2]==1){
                    info[2]=0;
                    info2.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info2.setBackgroundResource(R.drawable.button_soso);
                        info[2]=1;
                    }
                }

            }
        });
        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[3]==1){
                    info[3]=0;
                    info3.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info3.setBackgroundResource(R.drawable.button_soso);
                        info[3]=1;
                    }
                }

            }
        });
        info4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[4]==1){
                    info[4]=0;
                    info4.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info4.setBackgroundResource(R.drawable.button_soso);
                        info[4]=1;
                    }
                }

            }
        });
        info5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[5]==1){
                    info[5]=0;
                    info5.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info5.setBackgroundResource(R.drawable.button_soso);
                        info[5]=1;
                    }
                }

            }
        });
        info6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[6]==1){
                    info[6]=0;
                    info6.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info6.setBackgroundResource(R.drawable.button_soso);
                        info[6]=1;
                    }
                }

            }
        });
        info7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[7]==1){
                    info[7]=0;
                    info7.setBackgroundResource(R.drawable.button_noselected);

                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info7.setBackgroundResource(R.drawable.button_soso);
                        info[7]=1;
                    }
                }

            }
        });
        info8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[8]==1){
                    info[8]=0;
                    info8.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info8.setBackgroundResource(R.drawable.button_soso);
                        info[8]=1;
                    }
                }

            }
        });
        info9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[9]==1){
                    info[9]=0;
                    info9.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info9.setBackgroundResource(R.drawable.button_soso);
                        info[9]=1;
                    }
                }

            }
        });
        info10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[10]==1){
                    info[10]=0;
                    info10.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info10.setBackgroundResource(R.drawable.button_soso);
                        info[10]=1;
                    }
                }

            }
        });
        info11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[11]==1){
                    info[11]=0;
                    info11.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info11.setBackgroundResource(R.drawable.button_soso);
                        info[11]=1;
                    }
                }

            }
        });
        info12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info[12]==1){
                    info[12]=0;
                    info12.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:info){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        info12.setBackgroundResource(R.drawable.button_soso);
                        info[12]=1;
                    }
                }

            }
        });

        //here

        stress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[1]==1){
                    stress[1]=0;
                    stress1.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress1.setBackgroundResource(R.drawable.button_soso);
                        stress[1]=1;
                    }
                }

            }
        });
        stress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[2]==1){
                    stress[2]=0;
                    stress2.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress[2]=1;
                        stress2.setBackgroundResource(R.drawable.button_soso);
                    }
                }

            }
        });
        stress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[3]==1){
                    stress[3]=0;
                    stress3.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress[3]=1;
                        stress3.setBackgroundResource(R.drawable.button_soso);
                    }
                }

            }
        });
        stress4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[4]==1){
                    stress[4]=0;
                    stress4.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress4.setBackgroundResource(R.drawable.button_soso);
                        stress[4]=1;
                    }
                }

            }
        });
        stress5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[5]==1){
                    stress[5]=0;
                    stress5.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress[5]=1;
                        stress5.setBackgroundResource(R.drawable.button_soso);
                    }
                }

            }
        });
        stress6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[6]==1){
                    stress[6]=0;
                    stress6.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress6.setBackgroundResource(R.drawable.button_soso);
                        stress[6]=1;
                    }
                }

            }
        });
        stress7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[7]==1){
                    stress[7]=0;
                    stress7.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress7.setBackgroundResource(R.drawable.button_soso);
                        stress[7]=1;
                    }
                }

            }
        });
        stress8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[8]==1){
                    stress[8]=0;
                    stress8.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress8.setBackgroundResource(R.drawable.button_soso);
                        stress[8]=1;
                    }
                }

            }
        });
        stress9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[9]==1){
                    stress[9]=0;
                    stress9.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress9.setBackgroundResource(R.drawable.button_soso);
                        stress[9]=1;
                    }
                }

            }
        });
        stress10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[10]==1){
                    stress[10]=0;
                    stress10.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress10.setBackgroundResource(R.drawable.button_soso);
                        stress[10]=1;
                    }
                }

            }
        });
        stress11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[11]==1){
                    stress[11]=0;
                    stress11.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress11.setBackgroundResource(R.drawable.button_soso);
                        stress[11]=1;
                    }
                }

            }
        });
        stress12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stress[12]==1){
                    stress[12]=0;
                    stress12.setBackgroundResource(R.drawable.button_noselected);
                }
                else{
                    int sum=0;
                    for(int i:stress){
                        sum+=i;
                    }
                    if(sum>=3){
                        return;
                    }else if(sum<=3){
                        stress12.setBackgroundResource(R.drawable.button_soso);
                        stress[12]=1;
                    }
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!encodedString.equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.get(SettingActivity.this).clearDiskCache();
                        }
                    }).start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingActivity.this, "잠시만 기다려주세요", Toast.LENGTH_LONG).show();
                            NetworkHelper.getInstance().ChangeProfile(MainActivity.userToken, encodedString).enqueue(new Callback<Profile>() {
                                @Override
                                public void onResponse(Call<Profile> call, Response<Profile> response) {
                                    if (response.isSuccessful() && response != null) {
                                        Logger.e(response.toString());
                                        finish();
                                    } else {
                                        Logger.e(response.toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Profile> call, Throwable t) {

                                }
                            });
                        }
                    }, 500);
                }
                else {
                    //TODO 저장
                    finish();
                }

            }
        });
    }
}
