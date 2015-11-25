package com.alkathirikhalid.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private Button buttonRecord, buttonPlay;
    private ImageView imageView;
    private static String fileName;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying, isRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonRecord = (Button) findViewById(R.id.buttonRecord);
        buttonRecord.setOnClickListener(this);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecorder.3gp";
        isPlaying = true;
        isRecording = true;
        buttonPlay.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRecord:
                if (isRecording) {
                    startRecording();
                    // Set image view
                    buttonRecord.setText("Stop");
                } else {
                    stopRecording();
                    buttonRecord.setText("Record");
                    buttonPlay.setVisibility(View.VISIBLE);
                    buttonRecord.setVisibility(View.INVISIBLE);
                }
                isRecording = !isRecording;
                break;
            case R.id.buttonPlay:
                if (isPlaying) {
                    startPlaying();
                    // set image view
                    buttonPlay.setText("Stop");
                } else {
                    stopPlaying();
                    buttonPlay.setText("Play");
                    buttonPlay.setVisibility(View.INVISIBLE);
                    buttonRecord.setVisibility(View.VISIBLE);
                }
                isPlaying = !isPlaying;
                break;
        }

    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();

    }

    private void stopRecording() {
        mediaRecorder.release();

    }

    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        try {
            mediaPlayer.setDataSource(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

    }

    private void stopPlaying() {
        mediaPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaRecorder != null) {
            mediaRecorder.release();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopPlaying();
        buttonPlay.setText("Play");
        buttonPlay.setVisibility(View.INVISIBLE);
        buttonRecord.setVisibility(View.VISIBLE);
        isPlaying = !isPlaying;
    }
}
