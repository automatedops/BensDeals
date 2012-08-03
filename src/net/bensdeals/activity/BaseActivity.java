package net.bensdeals.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import com.google.inject.Inject;
import net.bensdeals.network.ImageLoader;
import net.bensdeals.utils.Reporter;
import roboguice.activity.RoboActivity;

public abstract class BaseActivity extends RoboActivity {
    protected @Inject ImageLoader imageLoader;
    protected @Inject Reporter reporter;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reporter.startSession(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        imageLoader.cancelOutstandingRequests();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reporter.endSession(this);
    }

    protected void createLoadingDialog(String message) {
        dialog = ProgressDialog.show(this, "", message, true, true);
    }
}
