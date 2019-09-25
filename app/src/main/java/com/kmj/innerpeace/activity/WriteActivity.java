package com.kmj.innerpeace.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.kmj.innerpeace.Data.DiaryData;
import com.kmj.innerpeace.Data.PostData;
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
import java.util.ArrayList;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {
    Button emotion, verygood, good, soso, bad, verybad;
    ImageView camera, img;
    Button upload;
    String e = "";
    ArrayList<String> permissions;
    String encodedString = "";
    Boolean havePic = false;
    Boolean havePost = false;
    EditText title, content;

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
        emotion = findViewById(R.id.write_emotion);
        verygood = findViewById(R.id.verygimotti);
        permissions = new ArrayList<>();
        good = findViewById(R.id.gimotti);
        soso = findViewById(R.id.soso);
        bad = findViewById(R.id.bad);
        camera = findViewById(R.id.write_camera);
        img = findViewById(R.id.write_image);
        verybad = findViewById(R.id.verybad);
        title = findViewById(R.id.write_title);
        content = findViewById(R.id.write_story);
        upload = findViewById(R.id.write_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!havePost) {
                    havePost=true;
                    if (!title.getText().toString().replace(" ", "").equals("")) {
                        if (!content.getText().toString().replace(" ", "").equals("")) {
                            if (!e.equals("")) {
                                if (havePic) {
                                    NetworkHelper.getInstance().uploadPost(MainActivity.userToken, title.getText().toString(),
                                            content.getText().toString(), e, encodedString).enqueue(new Callback<DiaryData>() {
                                        @Override
                                        public void onResponse(Call<DiaryData> call, Response<DiaryData> response) {
                                            if (response.isSuccessful() && response != null) {
                                                Toast.makeText(WriteActivity.this, "일기가 작성되었습니다.", Toast.LENGTH_SHORT).show();
                                                NetworkHelper.getInstance().getScore(MainActivity.userToken,response.body().getData().get_id()).enqueue(new Callback<PostData>() {
                                                    @Override
                                                    public void onResponse(Call<PostData> call, Response<PostData> response) {
                                                        if(response.isSuccessful() && response!=null){
                                                            MainActivity.refresh();
                                                        }
                                                        else{

                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<PostData> call, Throwable t) {

                                                    }
                                                });
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        finish();
                                                    }
                                                }, 500);
                                            } else if (response.isSuccessful()) {
                                                Toast.makeText(WriteActivity.this, "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                havePost = false;
                                                Logger.e(response.toString());
                                            } else {
                                                Toast.makeText(WriteActivity.this, "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                havePost = false;
                                                Logger.e(response.toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DiaryData> call, Throwable t) {

                                        }
                                    });
                                } else {
                                    Logger.e(MainActivity.userToken);
                                    NetworkHelper.getInstance().uploadPost(MainActivity.userToken, title.getText().toString(),
                                            content.getText().toString(), e, "").enqueue(new Callback<DiaryData>() {
                                        @Override
                                        public void onResponse(Call<DiaryData> call, Response<DiaryData> response) {
                                            if (response.isSuccessful() && response != null) {
                                                Toast.makeText(WriteActivity.this, "일기가 작성되었습니다.", Toast.LENGTH_SHORT).show();
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        finish();
                                                    }
                                                }, 500);
                                            } else if (response.isSuccessful()) {
                                                Toast.makeText(WriteActivity.this, "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                Logger.e(response.toString());
                                                havePost = false;
                                            } else {
                                                Toast.makeText(WriteActivity.this, "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                havePost = false;
                                                Logger.e(response.toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DiaryData> call, Throwable t) {

                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(WriteActivity.this, "감정을 선택하세요", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(WriteActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(WriteActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        verygood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.button_verygood);
                emotion.setText("매우 좋음");
                e = "매우 좋음";
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emotion.setBackgroundResource(R.drawable.button_good);
                emotion.setText("좋음");
                e = "좋음";
            }
        });
        soso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.button_soso);
                emotion.setText("보통");
                e = "보통";
            }
        });
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.button_bad);
                emotion.setText("나쁨");
                e = "나쁨";
            }
        });
        verybad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setBackgroundResource(R.drawable.button_verybad);
                emotion.setText("매우 나쁨");
                e = "매우 나쁨";
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {

                    TedImagePicker.with(WriteActivity.this).start(new OnSelectedListener() {
                        @Override
                        public void onSelected(Uri uri) {

                            img.setVisibility(View.VISIBLE);
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) emotion.getLayoutParams();
                            layoutParams.dimensionRatio = "W,1:1";
                            emotion.setLayoutParams(layoutParams);
                            Logger.e(uri.toString());
                            Glide.with(WriteActivity.this)
                                    .load(uri)
                                    .placeholder(R.drawable.sea)
                                    .fitCenter()
                                    .into(img);
                            File file = new File(uri.getPath());
                            havePic = true;
                            toBase64(file);


                        }
                    });
                }

            }


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {

            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }

        }
        this.permissions.clear();

    }

    public Boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            String[] reqPermissionArray = new String[permissions.size()];
            reqPermissionArray = permissions.toArray(reqPermissionArray);
            ActivityCompat.requestPermissions(this, reqPermissionArray, 888);
            return false;
        } else if (permissions.size() == 0) {
            return true;
        }
        return false;
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
}
