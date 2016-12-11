package net.misykat.misykatbandung;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.misykat.misykatbandung.data.Track;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;

public class MainActivity extends AppCompatActivity {
    SoundCloudHelper helper = new SoundCloudHelper();
    List<Track> tracks = new ArrayList<>();
    DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

    public class TracksListArrayAdapter extends ArrayAdapter<Track> {
        Context context = MainActivity.this;

        public TracksListArrayAdapter() {
            super(MainActivity.this, R.layout.list_tracks, tracks);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("TrackListArrayAdapter", "getView " + position);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.list_tracks, parent, false);
            TextView speaker = (TextView) rowView.findViewById(R.id.speaker);
            TextView title = (TextView) rowView.findViewById(R.id.title);
            TextView date = (TextView) rowView.findViewById(R.id.date);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

            Track track = tracks.get(position);

            if (track.getSpeakerName() == null) {
                speaker.setText("Misykat Bandung");
                title.setText(track.getFullTitle());
            } else {
                speaker.setText(track.getSpeakerName());
                title.setText(track.getTitle());
            }
            date.setText(dateFormat.format(track.getCreatedAt().getTime()));

            new DownloadImageTask(imageView)
                    .execute(track.getArtworkUrl());

            return rowView;
        }

        @Nullable
        @Override
        public Track getItem(int position) {
            return tracks.get(position);
        }
    }

    static WeakHashMap<String, Bitmap> bitmaps = new WeakHashMap<>();
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = bitmaps.get(urldisplay);
            if (mIcon11 == null) {
                // TODO: kalau ada beberapa yang bersamaan mengambil
                //       URL yang sama, satu saja yang ambil
                try {
                    InputStream in = new URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                    bitmaps.put(urldisplay, mIcon11);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LoadTracksTask().execute();
    }

    class LoadTracksTask extends AsyncTask<String, Void, List<Track>> {
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
            if (tracks == null) {
                ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.GONE);
                return;
            }
            MainActivity.this.tracks.clear();
            MainActivity.this.tracks.addAll(tracks);
            Track track = tracks.get(new Random().nextInt(tracks.size()));

            ListView list = (ListView) findViewById(R.id.list_view);
            list.setAdapter(new TracksListArrayAdapter());

            ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.GONE);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Track track = (Track) adapterView.getItemAtPosition(i);

                    Uri uri = Uri.parse(track.getPermalinkUrl()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
    }
}
