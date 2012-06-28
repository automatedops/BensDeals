package net.bensdeals.activity;

import android.os.Bundle;
import android.view.Window;
import com.google.inject.Inject;
import net.bensdeals.network.ImageLoader;
import roboguice.activity.RoboActivity;

public abstract class BaseActivity extends RoboActivity {
    protected @Inject ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        imageLoader.cancelOutstandingRequests();
    }
}
