package net.bensdeals.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.adapter.GalleryAdapter;
import net.bensdeals.model.Deal;
import net.bensdeals.util.ALog;
import net.bensdeals.utils.LayoutInflaterWithInjection;
import net.bensdeals.views.IndicatorView;
import net.bensdeals.views.gallery.GalleryView;
import roboguice.inject.InjectView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static net.bensdeals.model.Deal.parseXml;

public class DealGalleryActivity extends BaseActivity {
    @Inject LayoutInflaterWithInjection<View> inflater;
    @Inject GalleryAdapter adapter;
    @InjectView(R.id.deal_gallery) GalleryView galleryView;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflater.inflate(R.layout.deal_gallery_layout));
        galleryView.setOnIndexChangedListener(new GalleryOnIndexChanged());
        galleryView.setAdapter(adapter);
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
                }
            }
        }.execute(((Void) null));
    }

    protected void createLoadingDialog() {
        dialog = ProgressDialog.show(this, "", "Loading... ");
    }

    private class GalleryOnIndexChanged implements GalleryView.OnIndexChanged {
        @Override
        public void indexChanged(int leftIndex, int rightIndex) {
            int position = (rightIndex + leftIndex) / 2 + 1;
            indicatorView.setSelected(position > 0 ? position : 1);
        }
    }
}
