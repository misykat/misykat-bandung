package net.misykat.misykatbandung;

import net.misykat.misykatbandung.data.Track;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundCloudHelper {
    // XXX: sementara menggunakan soundcloud.com client_id karena
    // belum dapet dari soundcloudnya
    public static String CLIENT_ID = "fDoItMDbsbZz8dY16ZzARCZmzgHBPotA";
    public static String BASE_URL = "https://api-v2.soundcloud.com/";
    public static String USER_ID = "170413650";
    private JSONObject getTracksJson() throws IOException, JSONException {
        URL url = null;
        try {
            url = new URL(BASE_URL + "/users/" + USER_ID + "/tracks?client_id=" + CLIENT_ID);
        } catch (MalformedURLException e) {
            // nggak mungkin, jadi throw lagi dengan runtime exception
            throw new RuntimeException(e);
        }
        HttpURLConnection urlConnection = null;
        urlConnection = (HttpURLConnection) url.openConnection();
        try {
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            return new JSONObject(sb.toString());
        } finally {
            urlConnection.disconnect();
        }
    }

    public List<Track> getTracks() {
        return new ArrayList<>();
    }
}
