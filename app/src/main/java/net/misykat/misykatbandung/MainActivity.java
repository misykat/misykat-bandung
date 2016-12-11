package net.misykat.misykatbandung;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.misykat.misykatbandung.data.Track;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SoundCloudHelper helper = new SoundCloudHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    class PlayRandomTask extends AsyncTask<String, Void, List<Track>> {
        Exception e;

        @Override
        protected List<Track> doInBackground(String... strings) {
            try {
                List<Track> tracks = helper.getTracks();
                return tracks;
            } catch (IOException|JSONException e) {
                e.printStackTrace();
                this.e = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            TextView textView = (TextView) findViewById(R.id.text);
            if (tracks == null) {
                textView.setText("No track returned (error??= " + (e != null? e.getMessage() : "") + ")");
                return;
            }
            Track track = tracks.get(new Random().nextInt(tracks.size()));

            StringBuilder sb = new StringBuilder();
            for (Track t : tracks) {
                sb.append(t.getFullTitle() + "\n");
            }

            textView.setText(sb.toString());

            // panggil activity untuk play track
        }
    }

    public void onPlayRandomClick(View v) {
        new PlayRandomTask().execute();
    }
}
