package com.kmj.innerpeace.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.activity.MainActivity;
import com.kmj.innerpeace.activity.SettingActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    ImageView setting;
    MainActivity mainActivity;
    CircleImageView myProfile;
    Button info1,info2,info3,stress1,stress2,stress3;
    TextView name;
    PieChart pieChart1,pieChart2,pieChart3;
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
                if(MainActivity.isParentAccount==1){
                    Toast.makeText(mainActivity, "부모 계정은 사용할수없는 기능입니다.", Toast.LENGTH_SHORT).show();
                }
                else{

                Intent intent=new Intent(mainActivity, SettingActivity.class);
                mainActivity.startActivityForResult(intent,321);
                }

            }
        });
        info1=v.findViewById(R.id.profile_info1);
        info2=v.findViewById(R.id.profile_info2);
        info3=v.findViewById(R.id.profile_info3);
        stress1=v.findViewById(R.id.profile_stress1);
        stress2=v.findViewById(R.id.profile_stress2);
        stress3=v.findViewById(R.id.profile_stress3);
        pieChart1=v.findViewById(R.id.piechart1);
        pieChart2=v.findViewById(R.id.piechart2);
        pieChart3=v.findViewById(R.id.piechart3);
        setPieChart(1,1,1);
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

    public void setPieChart(int p1,int p2,int p3){
        pieChart1.setUsePercentValues(true);
        pieChart1.getDescription().setEnabled(false);
        pieChart1.setExtraOffsets(5,10,5,5);
        pieChart1.setDragDecelerationFrictionCoef(0.95f);
        pieChart1.setDrawHoleEnabled(false);
        pieChart1.setHoleColor(Color.WHITE);
        pieChart1.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues1 = new ArrayList<PieEntry>();
        yValues1.add(new PieEntry(40f,"집중O"));
        yValues1.add(new PieEntry(60f,"집중X"));
        pieChart1.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션
        PieDataSet dataSet1 = new PieDataSet(yValues1,"");
        dataSet1.setSliceSpace(3f);
        dataSet1.setSelectionShift(5f);
        dataSet1.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data1 = new PieData((dataSet1));
        data1.setValueTextSize(13f);
        pieChart1.setDrawSliceText(false); // To remove slice text
        pieChart1.setDrawMarkers(false); // To remove markers when click
        pieChart1.setDrawEntryLabels(false); // To remove labels from piece of pie
        pieChart1.getDescription().setEnabled(false); // To remove description of pie
        pieChart1.setData(data1);

        pieChart2.setUsePercentValues(true);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.setExtraOffsets(5,10,5,5);
        pieChart2.setDragDecelerationFrictionCoef(0.95f);
        pieChart2.setDrawHoleEnabled(false);
        pieChart2.setHoleColor(Color.WHITE);
        pieChart2.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues2 = new ArrayList<PieEntry>();
        yValues2.add(new PieEntry(40f,"집중O"));
        yValues2.add(new PieEntry(60f,"집중X"));
        pieChart2.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션
        PieDataSet dataSet2 = new PieDataSet(yValues2,"");
        dataSet2.setSliceSpace(3f);
        dataSet2.setSelectionShift(5f);
        dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data2 = new PieData((dataSet2));
        data2.setValueTextSize(13f);
        pieChart2.setDrawSliceText(false); // To remove slice text
        pieChart2.setDrawMarkers(false); // To remove markers when click
        pieChart2.setDrawEntryLabels(false); // To remove labels from piece of pie
        pieChart2.getDescription().setEnabled(false); // To remove description of pie
        pieChart2.setData(data2);

        pieChart3.setUsePercentValues(true);
        pieChart3.getDescription().setEnabled(false);
        pieChart3.setExtraOffsets(5,10,5,5);
        pieChart3.setDragDecelerationFrictionCoef(0.95f);
        pieChart3.setDrawHoleEnabled(false);
        pieChart3.setHoleColor(Color.WHITE);
        pieChart3.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues3 = new ArrayList<PieEntry>();
        yValues3.add(new PieEntry(40f,"집중O"));
        yValues3.add(new PieEntry(60f,"집중X"));
        pieChart3.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션
        PieDataSet dataSet3 = new PieDataSet(yValues3,"");
        dataSet3.setSliceSpace(3f);
        dataSet3.setSelectionShift(5f);
        dataSet3.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data3 = new PieData((dataSet3));
        data3.setValueTextSize(13f);
        pieChart3.setDrawSliceText(false); // To remove slice text
        pieChart3.setDrawMarkers(false); // To remove markers when click
        pieChart3.setDrawEntryLabels(false); // To remove labels from piece of pie
        pieChart3.getDescription().setEnabled(false); // To remove description of pie
        pieChart3.setData(data3);
    }
}

