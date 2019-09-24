package com.kmj.innerpeace.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kmj.innerpeace.R;
import com.kmj.innerpeace.activity.WriteActivity;
import com.kmj.innerpeace.activity.MainActivity;
import com.loopeer.shadow.ShadowView;

public class DiaryFragment extends Fragment {
    MainActivity mainActivity;
    ShadowView create;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_diary, container, false);
        mainActivity= (MainActivity) getActivity();
        create=v.findViewById(R.id.create_diary);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mainActivity, WriteActivity.class);
                mainActivity.startActivity(intent);
            }
        });

        return v;
    }

}