package com.kmj.innerpeace.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kmj.innerpeace.FragmentUtils;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.fragment.DiaryFragment;
import com.kmj.innerpeace.fragment.HomeFragment;
import com.kmj.innerpeace.fragment.MusicFragment;
import com.kmj.innerpeace.fragment.PlaylistFragment;
import com.kmj.innerpeace.fragment.ProfileFragment;
import com.kmj.innerpeace.util.BackPressCloseHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ArrayList<Fragment> fragments;
    FragmentUtils fragmentUtils;
    ProfileFragment profileFragment;
   static DiaryFragment diaryFragment;
    HomeFragment homeFragment;
    MusicFragment musicFragment;
    PlaylistFragment playlistFragment;
    BackPressCloseHandler backPressCloseHandler;
    ArrayList<String> permissions;
    public static String userToken;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        diaryFragment.refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    void setup() {
        Window window = getWindow();
        Logger.addLogAdapter(new AndroidLogAdapter());
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#121319"));
        backPressCloseHandler = new BackPressCloseHandler(this);
        SharedPreferences prefs =getSharedPreferences("pref", MODE_PRIVATE);
        userToken = prefs.getString("userToken", "");

        fragments = new ArrayList<>();
        permissions = new ArrayList<>();
        profileFragment = new ProfileFragment();
        diaryFragment = new DiaryFragment();
        homeFragment = new HomeFragment();
        musicFragment = new MusicFragment();
        playlistFragment = new PlaylistFragment();
        fragments.add(profileFragment);
        fragments.add(diaryFragment);
        fragments.add(homeFragment);
        fragments.add(musicFragment);
        fragments.add(playlistFragment);
        fragmentUtils = new FragmentUtils(R.id.framelayout, fragments);
        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_profile:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
                        return true;
                    case R.id.bottom_diary:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 1, new Bundle());
                        return true;
                    case R.id.bottom_home:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 2, new Bundle());
                        return true;
                    case R.id.bottom_music:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 3, new Bundle());
                        return true;
                    case R.id.bottom_playlist:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 4, new Bundle());
                        return true;
                }
                return false;
            }
        });
        checkPermission();


    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
        //super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {

            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public  void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);

        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            String[] reqPermissionArray = new String[permissions.size()];
            reqPermissionArray = permissions.toArray(reqPermissionArray);
            ActivityCompat.requestPermissions(MainActivity.this, reqPermissionArray, 888);
        }
    }
    public static void refresh(){
        diaryFragment.refresh();
    }
}
