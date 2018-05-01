package ideatree.akashdeep.com.ideatreeassignment.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ideatree.akashdeep.com.ideatreeassignment.R;

public class BuyNowActivity extends AppCompatActivity {

    TextView mTrackPrice;
    TextView mCollectionPrice;
    TextView mTrackUrl;
    TextView mCollectionUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_now_activity);
        mTrackPrice = (TextView) findViewById(R.id.track_price_view);
        mCollectionPrice = (TextView) findViewById(R.id.collection_price_text_view);
        mCollectionUrl = (TextView) findViewById(R.id.collection_view_url);
        mTrackUrl = (TextView) findViewById(R.id.track_url_text);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {

                mTrackPrice.setText(extras.getString("track_price"));
                mTrackUrl.setText(extras.getString("track_url"));
                mCollectionPrice.setText(extras.getString("collection_price"));
                mCollectionUrl.setText(extras.getString("collection_url"));
            }
        }

    }
}
