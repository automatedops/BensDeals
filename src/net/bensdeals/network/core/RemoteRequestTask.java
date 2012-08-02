package net.bensdeals.network.core;

import android.os.AsyncTask;
import net.bensdeals.network.callbacks.TaskCallback;
import net.bensdeals.network.request.ApiRequest;
import net.bensdeals.network.response.ErrorResponse;
import net.bensdeals.network.response.Response;
import net.bensdeals.utils.ALog;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Map;
import java.util.Set;

class RemoteRequestTask<T> extends AsyncTask<Void, Void, Response<T>> {
    private final ApiRequest<T> apiRequest;
    private final TaskCallback<T> callback;
    private DefaultHttpClient httpClient;

    public RemoteRequestTask(DefaultHttpClient httpClient, ApiRequest<T> apiRequest, TaskCallback<T> callback) {
        this.httpClient = httpClient;
        this.apiRequest = apiRequest;
        this.callback = callback;
    }

    @Override
    protected Response<T> doInBackground(Void... voids) {
        try {
            String url = apiRequest.getUrl();
            HttpGet get = new HttpGet(url);
            Map<String, String> headers = apiRequest.getHeaders();
            Set<String> keySet = headers.keySet();
            for (String key : keySet) get.addHeader(key, headers.get(key));
            HttpResponse execute = httpClient.execute(get);
            Response<T> response = new Response<T>(execute.getStatusLine().getStatusCode(), execute.getEntity().getContent(), execute.getHeaders("Content-Encoding"),apiRequest.getResponseClass());
            response.assignEntity();
            return response;
        } catch (Exception ignored) {
            ALog.e(ignored);
        }
        return new ErrorResponse<T>();
    }

    @Override
    protected void onPostExecute(Response<T> response) {
        super.onPostExecute(response);
        if (response!= null && response.isSuccessResponse()) {
            callback.onTaskSuccess(response.getEntity());
        } else {
            callback.onTaskFailed();
        }
        callback.onTaskComplete();
    }

}
