package com.kmj.innerpeace.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.activity.MainActivity;
import com.kmj.innerpeace.activity.SettingActivity;
import com.orhanobut.logger.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    ImageView setting;
    MainActivity mainActivity;
    CircleImageView myProfile;
    Button info1,info2,info3,stress1,stress2,stress3;
    TextView name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        mainActivity= (MainActivity) getActivity();

        setting=v.findViewById(R.id.profile_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mainActivity, SettingActivity.class);
                mainActivity.startActivityForResult(intent,321);
            }
        });
        info1=v.findViewById(R.id.profile_info1);
        info2=v.findViewById(R.id.profile_info2);
        info3=v.findViewById(R.id.profile_info3);
        stress1=v.findViewById(R.id.profile_stress1);
        stress2=v.findViewById(R.id.profile_stress2);
        stress3=v.findViewById(R.id.profile_stress3);
        setInfoStress();
        myProfile=v.findViewById(R.id.profile_profile);
        name=v.findViewById(R.id.fragment_profile_name);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!MainActivity.imgPath.equals("")){
                    Glide.with(mainActivity)
                            .load(MainActivity.imgPath)
                            .placeholder(R.drawable.ic_profile)
                            .fitCenter()
                            .into(myProfile);
                }

                name.setText(MainActivity.name+"님");
            }
        }, 300);


        return v;
    }
    public void profileRefresh(){
        Glide.with(mainActivity)
                .load(MainActivity.imgPath)
                .placeholder(R.drawable.ic_profile)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .into(myProfile);
    }
    public void setInfoStress(){
        SharedPreferences prefs = mainActivity.getSharedPreferences("pref", MODE_PRIVATE);
        final String infoString= prefs.getString("infoString", "");
        final String stressString=prefs.getString("stressString","");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!infoString.equals("")){
                    String[] splitText = infoString.split(":");
                    Logger.e(String.valueOf(splitText.length));
                    switch (splitText.length){
                        case 1:
                            info1.setText(splitText[0]);
                            info1.setVisibility(View.VISIBLE);
                            info2.setVisibility(View.INVISIBLE);
                            info3.setVisibility(View.GONE);
                            break;
                        case 2:
                            info1.setText(splitText[0]);
                            info2.setText(splitText[1]);
                            info1.setVisibility(View.VISIBLE);
                            info2.setVisibility(View.VISIBLE);
                            info3.setVisibility(View.GONE);
                            break;
                        case 3:
                            info1.setVisibility(View.VISIBLE);
                            info2.setVisibility(View.VISIBLE);
                            info3.setVisibility(View.VISIBLE);
                            
                            info1.setText(splitText[0]);
                            info2.setText(splitText[1]);
                            info3.setText(splitText[2]);

                            break;


                    }


                }

                if(!stressString.equals("")){
                    String[] splitText = stressString.split(":");
                    switch (splitText.length){
                        case 1:
                            stress1.setText(splitText[0]);
                            stress1.setVisibility(View.VISIBLE);
                            stress2.setVisibility(View.INVISIBLE);
                            stress3.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            stress1.setText(splitText[0]);
                            stress2.setText(splitText[1]);
                            stress1.setVisibility(View.VISIBLE);
                            stress2.setVisibility(View.VISIBLE);
                            stress3.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            stress1.setVisibility(View.VISIBLE);
                            stress2.setVisibility(View.VISIBLE);
                            stress3.setVisibility(View.VISIBLE);

                            stress1.setText(splitText[0]);
                            stress2.setText(splitText[1]);
                            stress3.setText(splitText[2]);

                            break;


                    }
                }

            }
        }, 300);

    }
}

