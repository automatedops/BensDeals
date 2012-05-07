package net.bensdeals.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.adapter.GalleryAdapter;
import net.bensdeals.network.api.DealsApiRequest;
import net.bensdeals.network.callbacks.ApiResponseCallbacks;
import net.bensdeals.network.core.ApiResponse;
import net.bensdeals.views.IndicatorView;
import net.bensdeals.views.gallery.GalleryView;
import roboguice.inject.InjectView;

import static net.bensdeals.model.Deal.parseXml;

public class DealGalleryActivity extends BaseActivity {
    @Inject GalleryAdapter adapter;
    @InjectView(R.id.indicator) IndicatorView indicatorView;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_gallery_layout);
        final GalleryView galleryView = (GalleryView) findViewById(R.id.deal_gallery);
        galleryView.setOnIndexChangedListener(new GalleryView.OnIndexChanged() {
            @Override
            public void indexChanged(int leftIndex, int rightIndex) {
                int position = (rightIndex + leftIndex) / 2 + 1;
                indicatorView.setSelected(position > 0 ? position : 1);
            }
        });
        galleryView.setAdapter(adapter);
        createLoadingDialog();
        makeRequest();
    }


    protected void makeRequest() {
        remoteTask.makeRequest(new DealsApiRequest(), new ApiResponseCallbacks() {
            @Override
            public void onSuccess(ApiResponse response) {
                adapter.replaceAll(parseXml(response.getResponseBody()));
                indicatorView.setSelected(1);
            }

            @Override
            public void onComplete() {
                dialog.dismiss();
            }
        });
    }

    protected void createLoadingDialog() {
        dialog = ProgressDialog.show(this, "", "Loading... ");
    }
}
