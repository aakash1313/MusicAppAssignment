package ideatree.akashdeep.com.ideatreeassignment.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ideatree.akashdeep.com.ideatreeassignment.R;
import ideatree.akashdeep.com.ideatreeassignment.model.SongDetail;

/**
 * Created by akash on 4/27/2018.
 */

public class SongAdapter extends ArrayAdapter<SongDetail> {
    int resource;
    private Context mContext;
    private ArrayList<SongDetail> songList;
    private OnSongClickListener listener;

    public SongAdapter(Context context, int resource, ArrayList<SongDetail> songList, OnSongClickListener listener) {
        super(context, resource, songList);
        this.songList = songList;
        this.mContext = context;
        this.listener = listener;
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.song_list_view_item, null, true);
            holder = new ViewHolder();
            holder.songName = (TextView) convertView.findViewById(R.id.list_song_name);
            holder.mCollectionView = (TextView) convertView.findViewById(R.id.list_collection_name);
            holder.artworkImage = (ImageView) convertView.findViewById(R.id.artwork_URL);
            holder.mSongGenre = (TextView) convertView.findViewById(R.id.list_song_genre);
            holder.mPlay = (Button) convertView.findViewById(R.id.play_pause);
            holder.buyNow = (Button) convertView.findViewById(R.id.buy_now);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SongDetail song = getItem(position);
        String songImage = song.getSongArtWorkUrl();
        holder.songName.setText(song.getSongName());
        holder.mCollectionView.setText(song.getCollectionName());
        holder.mSongGenre.setText(song.getGenre());
        if (song.isPlaying()) {
            holder.mPlay.setBackgroundResource(R.drawable.pause);
        } else {
            holder.mPlay.setBackgroundResource(R.drawable.play);
        }
        holder.mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlayClick(v, position);
            }
        });
        holder.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buyNowButtonClick(v, position);
            }
        });
        if (songImage != null) ;
        {
            Picasso.with(mContext)
                    .load(songImage)
                    .transform(new CircleTransform())
                    .into(holder.artworkImage);
        }

        return convertView;

    }


    public interface OnSongClickListener {
        public void onPlayClick(View v, int position);

        public void buyNowButtonClick(View v, int position);
    }

    static class ViewHolder {
        private TextView songName;
        private ImageView artworkImage;
        private TextView mCollectionView;
        private TextView mSongGenre;
        private Button mPlay;
        private Button buyNow;

    }
}