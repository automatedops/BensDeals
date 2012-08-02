package net.bensdeals.network.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.bensdeals.network.callbacks.RemoteTaskCallback;
import net.bensdeals.network.callbacks.TaskCallback;
import net.bensdeals.network.request.ApiRequest;
import org.apache.http.impl.client.DefaultHttpClient;

@Singleton
public class RemoteTask {
    DefaultHttpClient httpClient;

    @Inject
    public RemoteTask(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void makeRequest(final String path, final RemoteTaskCallback callback) {
        new RemoteFeedRequestTask(path, callback).execute(((Void) null));
    }

    public <T> void makeRequest(final ApiRequest<T> apiRequest, final TaskCallback<T> callback) {
        new RemoteRequestTask<T>(httpClient, apiRequest, callback).execute((Void) null);
    }

}
