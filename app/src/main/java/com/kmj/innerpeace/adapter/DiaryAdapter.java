package com.kmj.innerpeace.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kmj.innerpeace.Data.PostData;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.activity.MainActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    ArrayList<PostData> mList;
    MainActivity mainActivity;

    public DiaryAdapter(ArrayList<PostData> mList, MainActivity mainActivity) {
        this.mList = mList;
        this.mainActivity = mainActivity;
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView name;
        TextView time,title,story,check;
        ImageView delete,image;
        Button emotion;

        public DiaryViewHolder(@NonNull View v) {
            super(v);
            this.circleImageView=v.findViewById(R.id.item_profile);
            this.title=v.findViewById(R.id.item_title);
            this.time=v.findViewById(R.id.item_time);
            this.image=v.findViewById(R.id.item_image);
            this.emotion=v.findViewById(R.id.item_emotion);
            this.name=v.findViewById(R.id.item_name);
            this.story=v.findViewById(R.id.item_story);
            this.delete=v.findViewById(R.id.item_delete);
            this.check=v.findViewById(R.id.item_check);
        }
    }

    @NonNull
    @Override
    public DiaryAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_item, parent, false);

        DiaryAdapter.DiaryViewHolder viewHolder = new DiaryAdapter.DiaryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int i) {
        PostData temp=mList.get(i);
        holder.time.setText(temp.getTimeString());
        holder.emotion.setText(temp.getEmotion());
        holder.title.setText(temp.getTitle());
        holder.story.setText(temp.getContent());
        switch (temp.getEmotion()){
            case "매우 나쁨":
                holder.emotion.setBackgroundResource(R.drawable.button_verybad);
                break;
            case "나쁨":
                holder.emotion.setBackgroundResource(R.drawable.button_bad);
                break;
            case "보통":
                holder.emotion.setBackgroundResource(R.drawable.button_soso);
                break;
            case "좋음":
                holder.emotion.setBackgroundResource(R.drawable.button_good);
                break;
            case "매우 좋음":
                holder.emotion.setBackgroundResource(R.drawable.button_verygood);
                break;
        }
        if(!temp.getImgPath().equals("")){
            holder.image.setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.emotion.getLayoutParams();
            layoutParams.dimensionRatio = "W,1:1";
            holder.emotion.setLayoutParams(layoutParams);
            Glide.with(mainActivity)
                    .load(temp.getImgPath())
                    .placeholder(R.drawable.sea)
                    .fitCenter()
                    .into(holder.image);
        }



    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}