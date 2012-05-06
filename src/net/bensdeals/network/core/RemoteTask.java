package net.bensdeals.network.core;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.bensdeals.network.callbacks.ApiResponseCallbacks;

/**
 * Created with IntelliJ IDEA.11.1
 * User: Wei W.
 * Date: 03/29/2012
 * Time: 21:44
 */
public class RemoteTask {
    public static final String TAG = RemoteTask.class.getSimpleName();
    private Provider<Http> httpProvider;

    @Inject
    public RemoteTask(Provider<Http> httpProvider) {
        this.httpProvider = httpProvider;
    }

    public void makeRequest(ApiRequest apiRequest, final ApiResponseCallbacks responseCallbacks) {
        new RemoteCallTask(responseCallbacks, httpProvider).execute(apiRequest);
    }
}
