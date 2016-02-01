/*
 * Copyright (c) 2015 Al-Kathiri Khalid www.alkathirikhalid.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.alkathirikhalid.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

/**
 * <p><strong>Android Uni-Foundation<strong/></p>
 * <p/>
 * <p>This application has two buttons that changes functionality using a Flag,
 * this allows users to use the same button to achieve multiple tasks,
 * at the same time the buttons activate and deactivate using visible and
 * invisible respectiviely, allowing users to work with one button at a time.</p>
 * <p/>
 * <p>This app will also expose candidates in using MediaRecorder and MediaPlayer
 * including the required permissions to save data to the device.</p>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    /**
     * Button to record and play audio
     */
    private Button buttonRecord, buttonPlay;
    /**
     * ImageView for visual feedback on audio playback
     */
    private ImageView imageView;
    /**
     * Audio file storage location
     */
    private static String fileName;
    /**
     * MediaRecorder Obj for recording functionality
     */
    private MediaRecorder mediaRecorder;
    /**
     * MediaPlayer Obj for playing functionality
     */
    private MediaPlayer mediaPlayer;
    /**
     * Toggle record and play button visiblity by using flags isPlaying
     * for play button, isRecording for record button
     */
    private boolean isPlaying, isRecording;

    /**
     * Find UI views, sets the file name and location,
     * sets flags to true, sets play Button visible
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find UI Views
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonRecord = (Button) findViewById(R.id.buttonRecord);
        buttonRecord.setOnClickListener(this);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
        // Sets the file name and location
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecorder.3gp";
        // Sets flags to true
        isPlaying = true;
        isRecording = true;
        // Sets play button to visible
        buttonPlay.setVisibility(View.INVISIBLE);
    }

    /**
     * Either Records or Plays, depending on button visibility
     * based on the set flag
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Record Button Triggered
            case R.id.buttonRecord:
                // is recording allowed
                if (isRecording) {
                    // Start recording
                    startRecording();
                    //TODO Set image view
                    // Set the record button text to STOP
                    buttonRecord.setText("Stop");
                    // Else Recoding not allowed
                } else {
                    // Stop Recording
                    stopRecording();
                    // Revert the text from Stop to Record
                    buttonRecord.setText("Record");
                    // Enable Play Button
                    buttonPlay.setVisibility(View.VISIBLE);
                    // Disable Record Button
                    buttonRecord.setVisibility(View.INVISIBLE);
                }
                /*
                * Any clicks from Record button happens once per record,
                * till reverted / enabled when playing
                */
                isRecording = !isRecording;
                break;
            // Play Button Triggered
            case R.id.buttonPlay:
                // is playing allowed
                if (isPlaying) {
                    // Start playing
                    startPlaying();
                    // Give visual feedback on audio playback in case volume set low
                    imageView.setImageResource(R.drawable.wave);
                    // Set the play button text to STOP
                    buttonPlay.setText("Stop");
                    // Else Playing not allowed
                } else {
                    // Stop Playing
                    stopPlaying();
                    // Remove visual feedback to nothing
                    imageView.setImageResource(0);
                    // Revert the text from Stop to Play
                    buttonPlay.setText("Play");
                    // Disable Play Button
                    buttonPlay.setVisibility(View.INVISIBLE);
                    // Enable Record Button
                    buttonRecord.setVisibility(View.VISIBLE);
                }
                /*
                * Any clicks from Play button happens once per play,
                * till reverted / enabled when recording
                */
                isPlaying = !isPlaying;
                break;
        }

    }

    /**
     * Start Recording
     */
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

    /**
     * Stop Recording
     */
    private void stopRecording() {
        mediaRecorder.release();

    }

    /**
     * Start Playing
     */
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

    /**
     * Stop Playing
     */
    private void stopPlaying() {
        mediaPlayer.release();
    }

    /**
     * Release resource when App is paused
     */
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

    /**
     * Listening on Play Completion
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        stopPlaying();
        imageView.setImageResource(0);
        buttonPlay.setText("Play");
        buttonPlay.setVisibility(View.INVISIBLE);
        buttonRecord.setVisibility(View.VISIBLE);
        isPlaying = !isPlaying;
    }
}
// TODO Lab Activity
 /*
 * Lab Activity 6
 * 1) Group common repetitive used methods in the MainActivity.java code
 * 2) Change hardcoded String in the MainActivity.java to a resource and call them from Strings.xml
 * 3) Add images to the code to utilize the imageView and (optional) buttons if you may so wish
 */
