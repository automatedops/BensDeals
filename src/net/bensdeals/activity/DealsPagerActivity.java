package net.bensdeals.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import net.bensdeals.provider.CacheDirProvider;
import net.bensdeals.provider.XMLPathProvider;
import net.bensdeals.utils.Reporter;
import net.bensdeals.views.ComboBox;
import net.bensdeals.views.IndicatorView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class DealsPagerActivity extends BaseActivity {
    @Inject DealsAdapter adapter;
    @Inject RemoteTask remoteTask;
    @Inject XMLPathProvider xmlPathProvider;
    @Inject CacheDirProvider cacheDirProvider;
    @Inject OnPageChangeListener onPageChangeListener;
    @InjectView(R.id.deals_view_pager) ViewPager viewPager;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    @InjectView(R.id.combo_box) ComboBox comboBox;
    protected ProgressDialog dialog;

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

    private void fetchSearch() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(new URI("https://www.googleapis.com/shopping/search/v1/public/products?key=AIzaSyCHiDtmHLFMNcrYx5asFzP5THm3KP1O534&country=US&q=mp3+player&alt=json"));
                    HttpResponse execute = defaultHttpClient.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return null;
            }

            public String fromStream(InputStream inputStream) throws IOException {
                int bufSize = 1028;
                byte[] buffer = new byte[bufSize];
                int inSize;
                StringBuilder stringBuilder = new StringBuilder();
                while ((inSize = inputStream.read(buffer)) > 0) {
                    stringBuilder.append(new String(buffer, 0, inSize));
                }
                return stringBuilder.toString();
            }
        }.execute((Void) null);
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
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
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

    private class OnIndexChangeListener implements net.bensdeals.listener.OnIndexChangeListener {
        @Override
        public void indexChanged(int index) {
            viewPager.setCurrentItem(index, true);
        }
    }
}
