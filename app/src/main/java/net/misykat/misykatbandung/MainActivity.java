package net.misykat.misykatbandung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import net.misykat.misykatbandung.data.Track;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SoundCloudHelper helper = new SoundCloudHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPlayRandomClick(View v) {
        List<Track> tracks = helper.getTracks();

        Track track = tracks.get(new Random().nextInt(tracks.size()));

        // panggil activity untuk play track
    }
}
