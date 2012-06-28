package net.bensdeals.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.adapter.DealsAdapter;
import net.bensdeals.model.Deal;
import net.bensdeals.util.ALog;
import net.bensdeals.views.IndicatorView;
import roboguice.inject.InjectView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static net.bensdeals.model.Deal.parseXml;

public class DealsPagerActivity extends BaseActivity {
    @Inject DealsAdapter adapter;
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
        new AsyncTask<Void, Void, List<Deal>>() {
            @Override
            protected List<Deal> doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://bensbargains.net/rss/");
                    URLConnection conn = url.openConnection();
                    InputStream inputStream = conn.getInputStream();
                    return parseXml(inputStream);
                } catch (Exception e) {
                    ALog.e(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Deal> deals) {
                super.onPostExecute(deals);
                dialog.dismiss();
                if (deals!= null && !deals.isEmpty()) {
                    adapter.replaceAll(deals);
                    indicatorView.setSelected(1);
                } else {
                    Toast.makeText(DealsPagerActivity.this, "Failed to load...", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(((Void) null));
    }

    protected void createLoadingDialog() {
        dialog = ProgressDialog.show(this, "", "Loading... ");
    }
}
