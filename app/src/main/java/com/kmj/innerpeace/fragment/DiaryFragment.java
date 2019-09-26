package com.kmj.innerpeace.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.kmj.innerpeace.Data.Diarys;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.activity.MainActivity;
import com.kmj.innerpeace.activity.WriteActivity;
import com.kmj.innerpeace.adapter.DiaryAdapter;
import com.kmj.innerpeace.retrofit.NetworkHelper;
import com.loopeer.shadow.ShadowView;
import com.orhanobut.logger.Logger;

import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryFragment extends Fragment {
    MainActivity mainActivity;
    ShadowView create;
    Context context;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DiaryAdapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    CircleImageView myProfile;
    TextView name;
    Diarys diarys;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity= (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_diary, container, false);
        mainActivity= (MainActivity) getActivity();
        swipeRefreshLayout=v.findViewById(R.id.diary_swipe);
        create=v.findViewById(R.id.create_diary);
        mRecyclerView=v.findViewById(R.id.diary_recyler);
        mLinearLayoutManager = new LinearLayoutManager(mainActivity);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);

        myProfile=v.findViewById(R.id.fragment_diary_myProfile);
        if (!MainActivity.imgPath.equals("")){
            Glide.with(mainActivity)
                    .load(MainActivity.imgPath)
                    .placeholder(R.drawable.ic_profile)
                    .fitCenter()
                    .into(myProfile);
        }

        name=v.findViewById(R.id.fragment_diary_name);
        name.setText(MainActivity.name+"님");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mainActivity, WriteActivity.class);
                mainActivity.startActivityForResult(intent,123);
            }
        });
        refresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return v;
    }
    public void refresh(){
        NetworkHelper.getInstance().getMyPosts(mainActivity.userToken).enqueue(new Callback<Diarys>() {
            @Override
            public void onResponse(Call<Diarys> call, Response<Diarys> response) {
                if(response.isSuccessful() && response!=null){
                    diarys=response.body();
                    Collections.reverse(diarys.data);
                    mAdapter=new DiaryAdapter(diarys.getData(), mainActivity);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    Logger.e(response.toString());
                    swipeRefreshLayout.setRefreshing(false);
                }
                else if(response.isSuccessful()){
                    Toast.makeText(mainActivity, "다시 시도하세요", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    Logger.e(response.toString());
                }
                else{
                    Toast.makeText(mainActivity, "다시 시도하세요", Toast.LENGTH_SHORT).show();
                    Logger.e(response.toString());
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Diarys> call, Throwable t) {

            }
        });
    }
    public void profileRefresh(){
        Glide.with(mainActivity)
                .load(MainActivity.imgPath)
                .placeholder(R.drawable.ic_profile)
                .fitCenter()
                .into(myProfile);
    }
}