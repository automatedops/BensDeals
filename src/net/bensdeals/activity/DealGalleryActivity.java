package net.bensdeals.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.AdapterView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.adapter.GalleryAdapter;
import net.bensdeals.network.api.DealsApiRequest;
import net.bensdeals.network.callbacks.ApiResponseCallbacks;
import net.bensdeals.network.core.ApiResponse;
import net.bensdeals.views.IndicatorView;
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
        ((AdapterView)findViewById(R.id.deal_gallery)).setAdapter(adapter);
        createLoadingDialog();
        makeRequest();
    }


    protected void makeRequest() {
        remoteTask.makeRequest(new DealsApiRequest(), new ApiResponseCallbacks() {
            @Override
            public void onSuccess(ApiResponse response) {
                adapter.replaceAll(parseXml(response.getResponseBody()));
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
