package net.bensdeals.network.core;

import android.os.AsyncTask;
import com.google.inject.Provider;
import net.bensdeals.network.callbacks.ApiResponseCallbacks;
import net.bensdeals.util.ALog;

import static net.bensdeals.network.core.Http.Response;

class RemoteCallTask extends AsyncTask<ApiRequest, Void, ApiResponse> {
    private final ApiResponseCallbacks responseCallbacks;
    private Provider<Http> httpProvider;

    public RemoteCallTask(ApiResponseCallbacks responseCallbacks, Provider<Http> httpProvider) {
        this.responseCallbacks = responseCallbacks;
        this.httpProvider = httpProvider;
    }

    @Override
    protected ApiResponse doInBackground(ApiRequest... apiRequests) {
        ApiRequest apiRequest = apiRequests[0];
        Response response;
        try {
            if (apiRequest.getPostBody() == null) {
                response = httpProvider.get().get(apiRequest);
            } else {
                response = httpProvider.get().post(apiRequest);
            }
            return new ApiResponse(response.getStatusCode(), response.getResponseStream());
        } catch (Exception e) {
            ALog.e(e);
        }
        return new ErrorApiResponse();
    }

    @Override
    protected void onPostExecute(ApiResponse apiResponse) {
        responseCallbacks.dispatch(apiResponse);
    }
}
