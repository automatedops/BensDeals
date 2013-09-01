package net.bensdeals.activity;

import javax.inject.Inject;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;
import butterknife.InjectView;
import net.bensdeals.R;
import net.bensdeals.adapter.DealListAdapter;
import net.bensdeals.model.Deal;
import net.bensdeals.network.RemoteTask;
import net.bensdeals.network.callbacks.RemoteTaskCallback;
import net.bensdeals.provider.CacheDirProvider;
import net.bensdeals.provider.XMLPathProvider;
import net.bensdeals.utils.Reporter;

public class DealPagerActivity extends BaseActivity {
    @Inject RemoteTask remoteTask;
    @Inject XMLPathProvider xmlPathProvider;
    @Inject CacheDirProvider cacheDirProvider;
    @InjectView(R.id.list) ListView mListView;
    private XMLPathProvider.XMLPath mXmlPath;
    private DealListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_pager_layout);
        cacheDirProvider.clear();
        reporter.report(Reporter.ON_APP_START);

        mAdapter = new DealListAdapter(this);
        SwingRightInAnimationAdapter animationAdapter = new SwingRightInAnimationAdapter(new SwingBottomInAnimationAdapter(mAdapter));
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(animationAdapter);

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
                mAdapter.replaceAll(list);
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
}
