package net.bensdeals.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.adapter.DealsAdapter;
import net.bensdeals.listener.OnPageChangeListener;
import net.bensdeals.model.Deal;
import net.bensdeals.network.core.RemoteTask;
import net.bensdeals.network.core.RemoteTaskCallback;
import net.bensdeals.provider.XMLPathProvider;
import net.bensdeals.utils.Reporter;
import net.bensdeals.views.ComboBox;
import net.bensdeals.views.IndicatorView;
import roboguice.inject.InjectView;

import java.util.List;

public class DealsPagerActivity extends BaseActivity {
    @Inject DealsAdapter adapter;
    @Inject RemoteTask remoteTask;
    @Inject XMLPathProvider xmlPathProvider;
    @Inject OnPageChangeListener onPageChangeListener;
    @InjectView(R.id.deals_view_pager) ViewPager viewPager;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    @InjectView(R.id.combo_box) ComboBox comboBox;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_pager_layout);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(onPageChangeListener.setIndicatorView(indicatorView));
        reporter.report(Reporter.ON_APP_START);
        fetchXML();
    }

    private void fetchXML() {
        createLoadingDialog();
        final XMLPathProvider.XMLPath xmlPath = xmlPathProvider.get();
        remoteTask.makeRequest(xmlPath.getPath(), new RemoteTaskCallback() {
            @Override
            public void onTaskSuccess(List<Deal> list) {
                comboBox.render(xmlPath);
                viewPager.setCurrentItem(0, true);
                adapter.replaceAll(list);
                resetAdapter();
            }

            @Override
            public void onTaskFailed() {
                new AlertDialog.Builder(DealsPagerActivity.this).setMessage(getString(R.string.fail_to_load, xmlPath.getTitle())).create().show();
            }

            @Override
            public void onTaskComplete() {
                dialog.dismiss();
            }
        });
    }

    protected void createLoadingDialog() {
        dialog = ProgressDialog.show(this, "", getString(R.string.loading, xmlPathProvider.get().getTitle()));
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
        resetAdapter();
    }

    private void resetAdapter() {
        viewPager.setAdapter(adapter);
        indicatorView.setSelected(0);
    }

    public void comboBoxOnClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_a_page)
                .setItems(R.array.xml_titles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        XMLPathProvider.XMLPath[] values = XMLPathProvider.XMLPath.values();
                        xmlPathProvider.set(values[values.length > i ? i : 0]);
                        fetchXML();
                    }
                }).create().show();
    }

}
