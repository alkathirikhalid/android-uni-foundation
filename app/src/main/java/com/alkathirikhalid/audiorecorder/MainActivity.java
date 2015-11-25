package com.alkathirikhalid.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRecord, buttonPlay;
    private ImageView imageView;
    private static String fileName;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonRecord = (Button) findViewById(R.id.buttonRecord);
        buttonRecord.setOnClickListener(this);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "audiorecorder.3gp";
    }

    @Override
    public void onClick(View v) {

    }

    private void startRecording() {

    }

    private void stopRecording() {

    }

    private void startPlaying() {

    }

    private void stopPlaying() {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
