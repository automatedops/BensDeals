package net.bensdeals.activity;

import javax.inject.Inject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import butterknife.Views;
import net.bensdeals.utils.Reporter;

public abstract class BaseActivity extends Activity {
    protected @Inject Reporter reporter;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((DealApplication) getApplication()).inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Views.inject(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        Views.inject(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reporter.startSession(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
