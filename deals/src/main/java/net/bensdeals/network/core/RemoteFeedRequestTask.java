package net.bensdeals.network.core;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.os.AsyncTask;

import net.bensdeals.model.Deal;
import net.bensdeals.network.callbacks.RemoteTaskCallback;
import net.bensdeals.utils.ALog;

import static net.bensdeals.model.Deal.parseXml;

class RemoteFeedRequestTask extends AsyncTask<Void, Void, List<Deal>> {
    private final String path;
    private final RemoteTaskCallback callback;

    public RemoteFeedRequestTask(String path, RemoteTaskCallback callback) {
        this.path = path;
        this.callback = callback;
    }

    @Override
    protected List<Deal> doInBackground(Void... voids) {
        try {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            return parseXml(inputStream);
        } catch (Exception e) {
            ALog.e(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Deal> list) {
        if (list != null) {
            callback.onTaskSuccess(list);
        } else {
            callback.onTaskFailed();
        }
        callback.onTaskComplete();
    }
}
