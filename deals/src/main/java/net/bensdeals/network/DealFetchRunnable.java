package net.bensdeals.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.os.Handler;

import com.squareup.okhttp.OkHttpClient;
import net.bensdeals.model.Deal;
import net.bensdeals.network.callbacks.RemoteTaskCallback;

class DealFetchRunnable implements Runnable {
    private OkHttpClient mClient;
    private final String mPath;
    private final RemoteTaskCallback mRemoteTaskCallback;
    private Handler mHandler;

    public DealFetchRunnable(OkHttpClient client, String path, RemoteTaskCallback remoteTaskCallback, Handler handler) {
        mClient = client;
        mPath = path;
        mRemoteTaskCallback = remoteTaskCallback;
        mHandler = handler;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            HttpURLConnection connection = mClient.open(new URL(mPath));
            in = connection.getInputStream();
            final List<Deal> deals = Deal.parseXml(in);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRemoteTaskCallback.onTaskSuccess(deals);
                }
            });
        } catch (Exception e) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRemoteTaskCallback.onTaskFailed();
                }
            });
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
            }
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRemoteTaskCallback.onTaskComplete();
            }
        });
    }
}
