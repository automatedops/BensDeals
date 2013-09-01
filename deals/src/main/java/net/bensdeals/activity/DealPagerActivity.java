package net.bensdeals.activity;

import javax.inject.Inject;
import java.util.List;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import butterknife.InjectView;
import net.bensdeals.R;
import net.bensdeals.adapter.DealAdapter;
import net.bensdeals.listener.OnPageChangeListener;
import net.bensdeals.model.Deal;
import net.bensdeals.network.RemoteTask;
import net.bensdeals.network.callbacks.RemoteTaskCallback;
import net.bensdeals.provider.CacheDirProvider;
import net.bensdeals.provider.XMLPathProvider;
import net.bensdeals.utils.Reporter;
import net.bensdeals.views.IndicatorView;

public class DealPagerActivity extends BaseActivity {
    @Inject DealAdapter adapter;
    @Inject RemoteTask remoteTask;
    @Inject XMLPathProvider xmlPathProvider;
    @Inject CacheDirProvider cacheDirProvider;
    @Inject OnPageChangeListener onPageChangeListener;
    @InjectView(R.id.deals_view_pager) ViewPager viewPager;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    private XMLPathProvider.XMLPath mXmlPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_pager_layout);
        cacheDirProvider.clear();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(onPageChangeListener.setIndicatorView(indicatorView));
        indicatorView.setIndexChangeListener(new OnIndexChangeListener());
        reporter.report(Reporter.ON_APP_START);

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, xmlPathProvider.getNames());
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(spinnerAdapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                XMLPathProvider.XMLPath xmlPath = XMLPathProvider.XMLPath.values()[itemPosition];
                fetchXML(xmlPath);
                xmlPathProvider.set(xmlPath);
                return true;
            }
        });
        getSupportActionBar().setSelectedNavigationItem(xmlPathProvider.getLastPageIndex());
    }

    private void fetchXML(final XMLPathProvider.XMLPath xmlPath) {
        mXmlPath = xmlPath;
        createLoadingDialog(getString(R.string.loading, xmlPath.getTitle()));
        remoteTask.makeRequest(xmlPath.getPath(), new RemoteTaskCallback() {
            @Override
            public void onTaskSuccess(List<Deal> list) {
                viewPager.setCurrentItem(0, true);
                adapter.replaceAll(list);
                resetAdapter();
            }

            @Override
            public void onTaskFailed() {
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
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.item_refresh) {
            fetchXML(mXmlPath);
        }
        return super.onOptionsItemSelected(item);
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

    private class OnIndexChangeListener implements net.bensdeals.listener.OnIndexChangeListener {
        @Override
        public void indexChanged(int index) {
            viewPager.setCurrentItem(index, true);
        }
    }
}
