package com.hansung.android.practice10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    final int REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDangerousPermissions();



        Button musicPlayBtn = (Button)findViewById(R.id.musicPlayBtn);
        musicPlayBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                playAudioFromExternalStorage();

            }

        });

        Button videoPalyBtn = (Button)findViewById(R.id.videoPlayBtn);
        videoPalyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo();

            }
        });

    }

    private void playVideo(){

        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        String VIDEO_URL ="file//:"+
                Environment.getExternalStorageDirectory().getPath() +
                "/Music/"+"gitan.mp3";

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(VIDEO_URL));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer player) {

                videoView.seekTo(0);
                videoView.start();
            }
        });



    }

    private void playAudioFromExternalStorage(){
        Uri uri=Uri.parse("file//:"+
                Environment.getExternalStorageDirectory().getPath() +
                "/Music/"+"gitan.mp3");

        try {
            playAudio(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Uri uri=Uri.parse("file//:"+
//        Environment.getExternalStoragePublicDirectory());
    }

    private void killMediaPlayer() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onStop() {
        super.onStop();
        killMediaPlayer();
    }

    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    private void playAudio(Uri uri) throws Exception {
        killMediaPlayer();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(getApplicationContext(), uri);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA);

        }

    }
}
