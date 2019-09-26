package com.kmj.innerpeace.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.activity.MainActivity;
import com.kmj.innerpeace.activity.SettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    ImageView setting;
    MainActivity mainActivity;
    CircleImageView myProfile;
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
}
