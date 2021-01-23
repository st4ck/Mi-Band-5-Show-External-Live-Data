package com.cyberdynamic.stocks_player;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.media.MediaMetadataCompat;

import com.google.android.exoplayer2.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonTask extends AsyncTask<String, String, String> {

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonObject = new JSONObject(result);

            Log.d("res",Double.toString(jsonObject.getDouble("price")));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();
            PlayerService.metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE,  String.format("%1$,.2f", jsonObject.getDouble("price")) + " (" + String.format("%1$,.2f", jsonObject.getDouble("change_percent")) + "%)" + " [" + dtf.format(now) + "] OIL");
            PlayerService.mediaSession.setMetadata(PlayerService.metadataBuilder.build());
        } catch (JSONException e) {
            Log.d("JsonTask", e.getMessage());
        } catch (Exception e) {
            Log.d("JsonTask", e.getMessage());
        }
    }
}
