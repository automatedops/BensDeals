package net.bensdeals.activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.adapter.DealsAdapter;
import net.bensdeals.model.Deal;
import net.bensdeals.network.core.RemoteTask;
import net.bensdeals.network.core.RemoteTaskCallback;
import net.bensdeals.utils.Reporter;
import net.bensdeals.views.IndicatorView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import java.util.List;

public class DealsPagerActivity extends BaseActivity {
    @Inject DealsAdapter adapter;
    @Inject RemoteTask remoteTask;
    @InjectResource(R.string.url_homepage) String path;
    @InjectView(R.id.deals_view_pager) ViewPager viewPager;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_pager_layout);
        viewPager.setAdapter(adapter.setOnIndexChangedListener(indicatorView));
        reporter.report(Reporter.ON_APP_START);
        fetchXML();
    }

    private void fetchXML() {
        createLoadingDialog();
        remoteTask.makeRequest(path, new RemoteTaskCallback() {
            @Override
            public void onTaskSuccess(List<Deal> list) {
                adapter.replaceAll(list);
                indicatorView.setSelected(0);
            }

            @Override
            public void onTaskFailed() {
                Toast.makeText(DealsPagerActivity.this, R.string.fail_to_load, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTaskComplete() {
                dialog.dismiss();
            }
        });
    }

    protected void createLoadingDialog() {
        dialog = ProgressDialog.show(this, "", getString(R.string.loading));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        fetchXML();
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adapter.setOrientation(newConfig);
        viewPager.setAdapter(adapter);
    }
}
