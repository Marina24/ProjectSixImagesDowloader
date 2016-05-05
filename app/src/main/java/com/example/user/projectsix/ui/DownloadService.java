package com.example.user.projectsix.ui;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_ADD_IMAGES = 3;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        String[] urls = intent.getStringArrayExtra("url");

        Bundle bundle = new Bundle();
        if (!(urls.length == 0)) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
            try {
                for (int i = 0; i < urls.length; i++) {
                    byte[] oneImage = downloadData(urls[i]);

                /* Sending result back to activity */
                    if (null != oneImage && oneImage.length > 0) {
                        bundle.putByteArray("result", oneImage);
                        receiver.send(STATUS_ADD_IMAGES, bundle);
                    }
                }
            } catch (Exception e) {

                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }
        receiver.send(STATUS_FINISHED, Bundle.EMPTY);
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

    private byte[] downloadData(String requestUrl) throws IOException, DownloadException {
        InputStream inputStream = null;

        HttpURLConnection urlConnection = null;
        byte[] results;

        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

        int statusCode = urlConnection.getResponseCode();
            /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            results = getBytesFromInputStream(inputStream);

        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
        return results;
    }

    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
