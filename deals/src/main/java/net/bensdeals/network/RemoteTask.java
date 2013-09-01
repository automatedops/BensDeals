package net.bensdeals.network;

import java.util.concurrent.ExecutorService;

import android.os.Handler;

import com.squareup.okhttp.OkHttpClient;
import net.bensdeals.network.callbacks.RemoteTaskCallback;

public class RemoteTask {
    private OkHttpClient mClient;
    private ExecutorService mExecutorService;
    private Handler mHandler;

    public RemoteTask(OkHttpClient client, ExecutorService executorService, Handler handler) {
        mClient = client;
        mExecutorService = executorService;
        mHandler = handler;
    }

    public void makeRequest(final String path, final RemoteTaskCallback remoteTaskCallback) {
        mExecutorService.submit(new DealFetchRunnable(mClient, path, remoteTaskCallback, mHandler));
    }
}
