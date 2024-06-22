package com.dipak.onlyfortask;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.VideoView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VideoUtils {

    public static void playVideoFromStream(Context context, InputStream inputStream) {
        try {
            // Create a temporary file to save the video stream
            File videoFile = createTempVideoFile(context, inputStream);

            // Play the video using VideoView
            if (videoFile != null) {
                VideoView videoView = new VideoView(context);
                videoView.setVideoURI(Uri.fromFile(videoFile));
                videoView.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private static File createTempVideoFile(Context context, InputStream inputStream) throws IOException {
        // Create a temporary directory to save the video file
        File tempDir = new File(context.getExternalCacheDir(), "temp_videos");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        // Create a temporary file to save the video stream
        File tempFile = new File(tempDir, "temp_video.mp4");
        tempFile.createNewFile();

        // Write the InputStream to the temporary file
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }

        return tempFile;
    }
}

