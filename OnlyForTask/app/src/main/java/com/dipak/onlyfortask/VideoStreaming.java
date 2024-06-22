package com.dipak.onlyfortask;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dipak.onlyfortask.config.SSLUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoStreaming extends AppCompatActivity {
    private OkHttpClient client;
    private SeekBar seekBar;

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);
        seekBar = findViewById(R.id.seekBar);


        SSLUtils.trustAllCertificates();

        // Assuming you have initialized VideoView in your layout XML
         videoView = findViewById(R.id.videoView);

        client = new OkHttpClient();

        // Make request to fetch video
        Request request = new Request.Builder()
                .url("http://192.168.0.105:8080/video/dk.mp4")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Retrieve the video stream and play it
                    runOnUiThread(() -> {
                        try {
                            // Set the video URI and start playback
                            videoView.setVideoURI(Uri.parse("http://192.168.0.105:8080/video/dk.mp4"));
                            videoView.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Handle unsuccessful response
                }
            }
        });
        // Set media controller to show the video controls
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        // Set callback for seek events
        videoView.setOnPreparedListener(mp -> {
            int duration = mp.getDuration();
            seekBar.setMax(duration);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

    }
}