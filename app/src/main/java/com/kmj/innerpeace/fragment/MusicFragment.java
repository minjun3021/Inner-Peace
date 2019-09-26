package com.kmj.innerpeace.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kmj.innerpeace.MusicAdapter;
import com.kmj.innerpeace.MusicList;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.RecyclerItemClickListener;
import com.kmj.innerpeace.activity.MainActivity;

import java.util.ArrayList;

public class MusicFragment extends Fragment {
    RecyclerView recyclerView;
    int currentPosition;
    RecyclerView.LayoutManager layoutManager;
    TextView backdrop_Title, backdrop_Singer, textView, textView2;
    MediaPlayer mediaPlayer;
    LinearLayout backdrop;
    MainActivity musicmainActivity;
    public static Boolean isPlayed = false;
    LinearLayout allOfView;
    int touch_cnt = 0;
    ImageView backdrop_Image;
    ImageButton backdrop_previous_btn, backdrop_pause_btn, backdrop_next_btn, backdrop_play_btn;
    private ArrayList<MusicList> MusicInfoLIst;
    Boolean swiped;
    int swipeCnt = 0;
    private int pausePosition;
    MusicAdapter musicAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music, container, false);

        backdrop = v.findViewById(R.id.backdrop);
        backdrop_previous_btn = v.findViewById(R.id.backdrop_previous_btn);
        backdrop_pause_btn = v.findViewById(R.id.backdrop_pause_btn);
        backdrop_next_btn = v.findViewById(R.id.backdrop_next_btn);
        backdrop_play_btn = v.findViewById(R.id.backdrop_play_btn);


        backdrop_Singer = v.findViewById(R.id.backdrop_singer);
        backdrop_Title = v.findViewById(R.id.badkdrop_title);
        backdrop_Image = v.findViewById(R.id.image);

        allOfView = v.findViewById(R.id.linearlayout);
        musicmainActivity = (MainActivity) getActivity();
        recyclerView = v.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(musicmainActivity);
        recyclerView.setLayoutManager(layoutManager);
        MusicInfoLIst = new ArrayList<>();
        MusicInfoLIst.add(new MusicList(R.drawable.image2, "비트1", "비트1", R.drawable.ic_play_white, R.raw.song1));
        MusicInfoLIst.add(new MusicList(R.drawable.image4, "비트2", "비트2", R.drawable.ic_play_white, R.raw.song2));
        MusicInfoLIst.add(new MusicList(R.drawable.music_profile, "비트3", "비트3", R.drawable.ic_play_white, R.raw.song4));
        MusicInfoLIst.add(new MusicList(R.drawable.music_profile, "비트4", "비트4", R.drawable.ic_play_white, R.raw.song3));
        MusicInfoLIst.add(new MusicList(R.drawable.music_profile, "비트5", "비트5", R.drawable.ic_play_white, R.raw.song1));
        musicAdapter = new MusicAdapter(MusicInfoLIst);
        recyclerView.setAdapter(musicAdapter);
        musicAdapter.notifyDataSetChanged();

        backdrop_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backdrop_play_btn.setVisibility(View.GONE);
                backdrop_pause_btn.setVisibility(View.VISIBLE);

                mediaPlayer.start();
                mediaPlayer.seekTo(pausePosition);

            }
        });
        backdrop_pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backdrop_play_btn.setVisibility(View.VISIBLE);
                backdrop_pause_btn.setVisibility(View.GONE);

                mediaPlayer.pause();
                pausePosition = mediaPlayer.getCurrentPosition();


            }
        });
        backdrop_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size = MusicInfoLIst.size();
                int res = MusicInfoLIst.get(currentPosition).getMusic();
                Log.d("current is ", String.valueOf(currentPosition));

                if (isPlayed) {
                    mediaPlayer.stop();
                    isPlayed = false;
                    touch_cnt = 0;
                }
                if (++currentPosition > 4) {
                    currentPosition = 0;
                    mediaPlayer = MediaPlayer.create(musicmainActivity, MusicInfoLIst.get(currentPosition).getMusic());
                }
                mediaPlayer = MediaPlayer.create(musicmainActivity, MusicInfoLIst.get(currentPosition).getMusic());
                backdrop_Title.setText(MusicInfoLIst.get(currentPosition).getTitle());
                backdrop_Singer.setText(MusicInfoLIst.get(currentPosition).getSinger());
                backdrop_Image.setImageResource(MusicInfoLIst.get(currentPosition).getDrawableId());

                isPlayed = true;


                mediaPlayer.start();


            }
        });
        backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = backdrop_Title.getText().toString();
                String singer = backdrop_Singer.getText().toString();


                if (singer == null) {
                    Toast.makeText(musicmainActivity, "먼저 음악을 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else if (singer != null) {
                }
            }
        });

        backdrop_previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("-- current is ", String.valueOf(currentPosition));

                if (isPlayed) {
                    mediaPlayer.stop();
                    isPlayed = false;
                    touch_cnt = 0;
                }
                if (--currentPosition < 0) {
                    currentPosition = 4;
                    mediaPlayer = MediaPlayer.create(musicmainActivity, MusicInfoLIst.get(currentPosition).getMusic());
                }
                mediaPlayer = MediaPlayer.create(musicmainActivity, MusicInfoLIst.get(currentPosition).getMusic());
                backdrop_Title.setText(MusicInfoLIst.get(currentPosition).getTitle());
                backdrop_Singer.setText(MusicInfoLIst.get(currentPosition).getSinger());
                backdrop_Image.setImageResource(MusicInfoLIst.get(currentPosition).getDrawableId());

                isPlayed = true;


                mediaPlayer.start();

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            //
            @Override
            public void onItemClick(View view, final int position) {
                currentPosition = position;
                if (isPlayed) {
                    mediaPlayer.stop();
                    isPlayed = false;
                    touch_cnt = 0;
                }
                musicAdapter.clearSelectedItem();

                swiped = false;
                swipeCnt++;
                touch_cnt++;

                MusicInfoLIst.get(position).getMusic();

                int music = MusicInfoLIst.get(position).getMusic();


                if (isPlayed == false && touch_cnt == 1) {
                    mediaPlayer = MediaPlayer.create(musicmainActivity, MusicInfoLIst.get(position).getMusic());
                    mediaPlayer.start();

                    backdrop_Title.setText(MusicInfoLIst.get(position).getTitle());
                    backdrop_Singer.setText(MusicInfoLIst.get(position).getSinger());
                    backdrop_Image.setImageResource(MusicInfoLIst.get(position).getDrawableId());

                    isPlayed = true;
                } else if (isPlayed) {
                    mediaPlayer.stop();
                    isPlayed = false;
                    touch_cnt = 0;
                }


                if (!isPlayed) {

                    isPlayed = true;
                } else {

                }

                if (swiped == false && swipeCnt % 2 > 0) {
                    swipeCnt = 0;

                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

                swiped = true;
                swipeCnt++;
            }
        }));
        return v;
    }

    public void playMusic(int position) {
        if (isPlayed) {
            mediaPlayer.stop();
            isPlayed = false;
            touch_cnt = 0;
        }

        swiped = false;
        swipeCnt++;
        touch_cnt++;



        int music = MusicInfoLIst.get(position).getMusic();
        mediaPlayer = MediaPlayer.create(musicmainActivity, MusicInfoLIst.get(position).getMusic());
        mediaPlayer.start();
        backdrop_Title.setText(MusicInfoLIst.get(position).getTitle());
        backdrop_Singer.setText(MusicInfoLIst.get(position).getSinger());
        backdrop_Image.setImageResource(MusicInfoLIst.get(position).getDrawableId());

        isPlayed = true;



    }


}

