package net.misykat.misykatbandung;

import android.util.Log;

import net.misykat.misykatbandung.data.Track;

import org.json.JSONArray;
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
    private JSONObject getTracksJson(int offset, int num) throws IOException, JSONException {
        URL url = null;
        try {
            url = new URL(BASE_URL + "users/" + USER_ID + "/tracks?limit=" + num + "&client_id=" + CLIENT_ID + "&offset=" + offset);
        } catch (MalformedURLException e) {
            // nggak mungkin, jadi throw lagi dengan runtime exception
            throw new RuntimeException(e);
        }
        HttpURLConnection urlConnection = null;
        Log.d(getClass().getName(), "Downloading tracks from SoundCloud");
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

    public List<Track> getTracks() throws JSONException, IOException {
        Log.d(getClass().getName(), "Reading Json");
        JSONObject o = getTracksJson(0,200);
        JSONObject o2 = getTracksJson(200,200); // TODO: saat ini diasumsikan maks 400 tracks yang ada
        JSONArray arr = o.getJSONArray("collection");

        ArrayList<Track> ret = new ArrayList<>();
        for (int i=0; i<arr.length(); i++) {
            ret.add(Track.fromJson(arr.getJSONObject(i)));
        }

        Log.d(getClass().getName(), "Got " + arr.length() + " tracks");

        return ret;
    }
}
