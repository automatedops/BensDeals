package net.bensdeals.network.core;

import android.content.Context;
import android.os.AsyncTask;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.bensdeals.model.Deal;
import net.bensdeals.utils.ALog;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static net.bensdeals.model.Deal.parseXml;
@Singleton
public class RemoteTask {
    Context context;

    @Inject
    public RemoteTask(Context context) {
        this.context = context;
    }

    public void makeRequest(final String path, final RemoteTaskCallback callback) {
        new AsyncTask<Void, Void, List<Deal>>() {
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
        }.execute(((Void) null));
    }
}
