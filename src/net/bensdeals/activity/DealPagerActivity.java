package net.bensdeals.activity;

import android.app.AlertDialog;
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
import net.bensdeals.adapter.DealAdapter;
import net.bensdeals.listener.OnPageChangeListener;
import net.bensdeals.model.Deal;
import net.bensdeals.network.callbacks.RemoteTaskCallback;
import net.bensdeals.network.core.RemoteTask;
import net.bensdeals.provider.CacheDirProvider;
import net.bensdeals.provider.XMLPathProvider;
import net.bensdeals.utils.Reporter;
import net.bensdeals.views.ComboBox;
import net.bensdeals.views.IndicatorView;
import roboguice.inject.InjectView;

import java.util.List;

public class DealPagerActivity extends BaseActivity {
    @Inject DealAdapter adapter;
    @Inject RemoteTask remoteTask;
    @Inject XMLPathProvider xmlPathProvider;
    @Inject CacheDirProvider cacheDirProvider;
    @Inject OnPageChangeListener onPageChangeListener;
    @InjectView(R.id.deals_view_pager) ViewPager viewPager;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    @InjectView(R.id.combo_box) ComboBox comboBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cacheDirProvider.clear();
        setContentView(R.layout.deal_pager_layout);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(onPageChangeListener.setIndicatorView(indicatorView));
        indicatorView.setIndexChangeListener(new OnIndexChangeListener());
        reporter.report(Reporter.ON_APP_START);
        fetchXML();
    }

    private void fetchXML() {
        createLoadingDialog(getString(R.string.loading, xmlPathProvider.get().getTitle()));
        final XMLPathProvider.XMLPath xmlPath = xmlPathProvider.get();
        comboBox.render(xmlPath);
        remoteTask.makeRequest(xmlPath.getPath(), new RemoteTaskCallback() {
            @Override
            public void onTaskSuccess(List<Deal> list) {
                viewPager.setCurrentItem(0, true);
                adapter.replaceAll(list);
                resetAdapter();
            }

            @Override
            public void onTaskFailed() {
                comboBox.failedToLoad(xmlPath);
                if (!DealPagerActivity.this.isFinishing())
                    new AlertDialog.Builder(DealPagerActivity.this).setMessage(getString(R.string.fail_to_load, xmlPath.getTitle())).create().show();
            }

            @Override
            public void onTaskComplete() {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_refresh:
                fetchXML();
                break;
            case R.id.item_pages:
                comboBoxOnClick(null);
                break;
            default:
        }
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

    public void onSearchClick(View view) {
        startActivity(DealSearchActivity.intentFor(this, adapter.getCount() == 0 ? "" : adapter.getItem(viewPager.getCurrentItem()).getBrandName()));
    }

    private class OnIndexChangeListener implements net.bensdeals.listener.OnIndexChangeListener {
        @Override
        public void indexChanged(int index) {
            viewPager.setCurrentItem(index, true);
        }
    }
}
