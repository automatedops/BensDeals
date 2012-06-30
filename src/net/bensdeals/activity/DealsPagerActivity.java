package net.bensdeals.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.adapter.DealsAdapter;
import net.bensdeals.model.Deal;
import net.bensdeals.network.core.RemoteTask;
import net.bensdeals.network.core.RemoteTaskCallback;
import net.bensdeals.views.IndicatorView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import java.util.List;

public class DealsPagerActivity extends BaseActivity {
    @Inject DealsAdapter adapter;
    @Inject RemoteTask remoteTask;
    @InjectResource(R.string.url_homepage) String path;
    @InjectView(R.id.deals_view_pager) ViewPager galleryView;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_pager_layout);
        galleryView.setAdapter(adapter.setOnIndexChangedListener(indicatorView));
        createLoadingDialog();
        fetchXML();
    }

    private void fetchXML() {
        remoteTask.makeRequest(path, new RemoteTaskCallback() {
            @Override
            public void onTaskSuccess(List<Deal> list) {
                adapter.replaceAll(list);
                indicatorView.setSelected(1);
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
}
