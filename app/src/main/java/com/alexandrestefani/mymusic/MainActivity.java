package com.alexandrestefani.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSeekBar();


        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
    }

    public void playSong(View view){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }

    }

    public void pauseSong(View view){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }
    public void stopSong(View view){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
        }
    }

    private void initSeekBar(){
        seekBarVolume = findViewById(R.id.seekbar_volume);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //recuperar o volume máximo
        int volumeMax = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        int volumeCurrent = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);

        //configurar os valores maximos o seekbar
        seekBarVolume.setMax(volumeMax);
        seekBarVolume.setProgress(volumeCurrent);

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}