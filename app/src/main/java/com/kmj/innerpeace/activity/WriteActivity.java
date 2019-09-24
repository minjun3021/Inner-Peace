package com.kmj.innerpeace.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.kmj.innerpeace.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class WriteActivity extends AppCompatActivity {
    Button emotion,verygood,good,soso,bad,verybad;
    ImageView camera,img;
    String e="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        setup();
    }
    void setup() {
        Window window = getWindow();
        Logger.addLogAdapter(new AndroidLogAdapter());
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#121319"));
        emotion=findViewById(R.id.write_emotion);
        verygood=findViewById(R.id.verygimotti);
        good=findViewById(R.id.gimotti);
        soso=findViewById(R.id.soso);
        bad=findViewById(R.id.bad);
        camera=findViewById(R.id.write_camera);
        img=findViewById(R.id.write_image);
        verybad=findViewById(R.id.verybad);

        verygood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.bg_verygimotti);
                emotion.setText("매우 좋음");
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emotion.setBackgroundResource(R.drawable.button_verygood);
                emotion.setText("좋음");
            }
        });
        soso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.button_soso);
                emotion.setText("보통");
            }
        });
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.button_bad);
                emotion.setText("나쁨");
            }
        });
        verybad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.button_verybad);
                emotion.setText("매우 나쁨");
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setVisibility(View.VISIBLE);
                ConstraintLayout.LayoutParams layoutParams= (ConstraintLayout.LayoutParams) emotion.getLayoutParams();
                layoutParams.dimensionRatio="W,1:1";
                emotion.setLayoutParams(layoutParams);
            }
        });

    }
}
