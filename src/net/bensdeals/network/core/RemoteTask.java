package net.bensdeals.network.core;

import android.os.AsyncTask;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.bensdeals.network.request.ApiRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Singleton
public class RemoteTask {
    private DefaultHttpClient httpClient;

    @Inject
    public RemoteTask(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void makeRequest(final String path, final RemoteTaskCallback callback) {
        new RemoteFeedRequestTask(path, callback).execute(((Void) null));
    }

    public void makeRequest(final ApiRequest apiRequest, final RemoteTaskCallback callback) {
        new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... voids) {
                try {
                    String url = apiRequest.getUrl();
                    HttpGet get = new HttpGet(url);
                    Map<String, String> headers = apiRequest.getHeaders();
                    Set<String> keySet = headers.keySet();
                    for (String key : keySet) get.addHeader(key, headers.get(key));
                    Response response = new Response(httpClient.execute(get).getStatusLine().getStatusCode(), httpClient.execute(get).getEntity().getContent(), apiRequest.getResponseClass());
                    response.assignEntity();
                    return response;
                } catch (IOException ignored) {
                }
                return new Response(500, null, null);
            }

            @Override
            protected void onPostExecute(Response response) {
                super.onPostExecute(response);
                if (response.isSuccessResponse()) {
                    callback.onTaskSuccess(response);
                } else {
                    callback.onTaskFailed();
                }
                callback.onTaskComplete();
            }

        }.execute((Void) null);
    }

}
