package ideatree.akashdeep.com.ideatreeassignment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ideatree.akashdeep.com.ideatreeassignment.R;
import ideatree.akashdeep.com.ideatreeassignment.helpers.SongAdapter;
import ideatree.akashdeep.com.ideatreeassignment.model.SongDetail;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "APP_LOGS";
    private ArrayList<SongDetail> songList;
    private ListView mListView;
    private SongAdapter mAdapter;
    private int currentPlaying = -1;
    private int prevSong = -1;
    private MediaPlayer mediaPlayer;
    private boolean intialStage = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Array list which stores the details of all the song
        songList = new ArrayList<SongDetail>();
        //Get JSON data from the given url and parse the data into model i.e SongDetails
        getJSONDataFromUrl();
        mListView = (ListView) findViewById(R.id.list_view_songs);
        //initializa the media player object
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    private void getJSONDataFromUrl() {
        String url = "https://itunes.apple.com/search?term=Michael+jackson";
        //Volley request to get the JSON data
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response is" + response);
                        if (response != null && !response.equals("") && response.length() > 0) {
                            parseJSONData(response);
                            updateListView();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    /**
     * Logic to play and pause the song on user events
     */
    private void updateListView() {
        if (songList.size() > 0) {
            mAdapter = new SongAdapter(getApplicationContext(), R.layout.song_list_view_item, songList, new SongAdapter.OnSongClickListener() {
                @Override
                public void onPlayClick(View v, int position) {
                    prevSong = currentPlaying;
                    currentPlaying = position;
                    SongDetail item = songList.get(position);
                    if (prevSong == currentPlaying && prevSong != -1) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            item.setPlaying(false);
                        } else {
                            mediaPlayer.start();
                            item.setPlaying(true);
                        }
                    }
                    if (prevSong != currentPlaying) {
                        if (prevSong != -1) {
                            SongDetail s = songList.get(prevSong);
                            s.setPlaying(false);
                        }
                        item.setPlaying(true);
                        String songUrl = item.getPlayUrl();
                        if (songUrl != null && prevSong != currentPlaying) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();

                            new SongPlayTask()
                                    .execute(songUrl);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void buyNowButtonClick(View v, int position) {
                    Intent i = new Intent(MainActivity.this, BuyNowActivity.class);
                    SongDetail item = songList.get(position);
                    i.putExtra("track_price", item.getTrackPrice());
                    i.putExtra("collection_price", item.getCollectionPrice());
                    i.putExtra("track_url", item.getTrackViewUrl());
                    i.putExtra("collection_url", item.getCollectionViewUrl());
                    startActivity(i);

                }
            });
            mListView.setAdapter(mAdapter);
        }
    }

    /**
     * Parse JSON response to the arraylist
     * @param response
     */
    private void parseJSONData(String response) {
        try {
            JSONObject res = new JSONObject(response);
            JSONArray songs = res.getJSONArray("results");
            if (songs.length() > 0) {
                int len = songs.length();
                for (int i = 0; i < len; i++) {
                    SongDetail s = new SongDetail();
                    JSONObject song = songs.getJSONObject(i);
                    String trackName = song.getString("trackCensoredName");
                    String artWorkUrl = song.getString("artworkUrl60");
                    String collectionName = song.getString("collectionName");
                    String playUrl = song.getString("previewUrl");
                    String genre = song.getString("primaryGenreName");
                    String currency = song.getString("currency");
                    String trackPrice = song.getString("trackPrice") + " " + currency;
                    String collectionPrice = song.getString("collectionPrice") + " " + currency;
                    String trackViewUrl = song.getString("trackViewUrl");
                    String collectionViewUrl = song.getString("collectionViewUrl");
                    s.setGenre(genre);
                    s.setPlayUrl(playUrl);
                    s.setTrackViewUrl(trackViewUrl);
                    s.setCollectionViewUrl(collectionViewUrl);
                    s.setTrackPrice(trackPrice);
                    s.setCollectionPrice(collectionPrice);
                    s.setCollectionName(collectionName);
                    s.setSongName(trackName);
                    s.setSongArtWorkUrl(artWorkUrl);
                    songList.add(s);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async to stream the song and then play it
     */
    class SongPlayTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        public SongPlayTask() {
            progress = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub

                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            mediaPlayer.start();

            intialStage = false;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }
    }
}
