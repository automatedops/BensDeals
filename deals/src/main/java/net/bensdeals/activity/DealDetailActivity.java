package net.bensdeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.squareup.picasso.Picasso;
import butterknife.InjectView;
import net.bensdeals.R;
import net.bensdeals.model.Deal;
import net.bensdeals.model.ViewInfo;

public class DealDetailActivity extends BaseActivity {
    public static final String EXTRA_KEY_DEAL = "extra.deal";
    public static final String EXTRA_KEY_INFO = "extra.info";
    private Deal mDeal;
    private ViewInfo mViewInfo;

    @InjectView(R.id.image) ImageView mImageView;
    @InjectView(R.id.title) TextView mTitleView;
    @InjectView(R.id.subtitle) TextView mSubtitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mDeal = getIntent().getParcelableExtra(EXTRA_KEY_DEAL);
            mViewInfo = getIntent().getParcelableExtra(EXTRA_KEY_INFO);
        } else {
            mDeal = savedInstanceState.getParcelable(EXTRA_KEY_DEAL);
            mViewInfo = savedInstanceState.getParcelable(EXTRA_KEY_INFO);
        }

        setContentView(R.layout.deal_detail);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        Picasso.with(this).load(mDeal.getImageUrl()).into(mImageView);

        mTitleView.setText(Html.fromHtml(mDeal.getTitle()));
        mSubtitleView.setText(Html.fromHtml(mDeal.getDescription()));
        startAnimation();
    }

    private void startAnimation() {
        mTitleView.setVisibility(View.VISIBLE);
        mSubtitleView.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2) {
            return;
        }

//        mImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
//
//                int[] screenLocation = new int[2];
//                mImageView.getLocationOnScreen(screenLocation);
//
//                float leftDelta = mViewInfo.getLeft() - screenLocation[0];
//                float topDelta = mViewInfo.getTop() - screenLocation[1];
//
//                float widthScale = mViewInfo.getWidth() / mImageView.getWidth();
//                float heightScale = mViewInfo.getHeight() / mImageView.getHeight();
//
//
//                DisplayMetrics metrics = getResources().getDisplayMetrics();
//                int width = mImageView.getWidth();
//
//
//                mImageView.setPivotX(0);
//                mImageView.setPivotY(0);
//                mImageView.setScaleX(widthScale);
//                mImageView.setScaleY(heightScale);
//                mImageView.setTranslationX(leftDelta);
//                mImageView.setTranslationY(topDelta);
//                mImageView.setAlpha(0.25f);
//
//                mImageView.animate()
//                        .setDuration(700)
//                        .scaleX(1)
//                        .scaleY(1)
//                        .translationX((metrics.widthPixels - width) / 2).translationY(0)
//                        .setInterpolator(new AccelerateInterpolator()).alpha(1)
//                        .setListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animator) {
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animator) {
//                                mTitleView.setAlpha(0);
//                                mSubtitleView.setAlpha(0);
//                                mTitleView.animate().alpha(1f).setDuration(300l);
//                                mSubtitleView.animate().alpha(1f).setDuration(300l);
//
//                                mTitleView.setVisibility(View.VISIBLE);
//                                mSubtitleView.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animator) {
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animator) {
//                            }
//                        });
//                return true;
//            }
//        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_KEY_DEAL, mDeal);
        outState.putParcelable(EXTRA_KEY_INFO, mViewInfo);
        super.onSaveInstanceState(outState);
    }

    public static void start(Context context, Deal item, ViewInfo viewInfo) {
        context.startActivity(new Intent(context, DealDetailActivity.class).putExtra(EXTRA_KEY_DEAL, item).putExtra(EXTRA_KEY_INFO, viewInfo));
    }
}
