package net.bensdeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.squareup.picasso.Picasso;
import butterknife.InjectView;
import net.bensdeals.R;
import net.bensdeals.model.Deal;

public class DealDetailActivity extends BaseActivity {
    private Deal mDeal;

    @InjectView(R.id.image) ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mDeal = getIntent().getParcelableExtra("extra.deal");
        } else {
            mDeal = savedInstanceState.getParcelable("extra.deal");
        }

        setContentView(R.layout.deal_detail);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Picasso.with(this).load(mDeal.getImageUrl()).into(mImageView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("extra.deal", mDeal);
        super.onSaveInstanceState(outState);
    }

    public static void start(Context context, Deal item) {
        context.startActivity(new Intent(context, DealDetailActivity.class).putExtra("extra.deal", item));
    }
}
