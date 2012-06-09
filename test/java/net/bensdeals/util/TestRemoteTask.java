package net.bensdeals.util;

import android.util.Pair;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import net.bensdeals.network.callbacks.ApiResponseCallbacks;
import net.bensdeals.network.core.ApiRequest;
import net.bensdeals.network.core.ApiResponse;
import net.bensdeals.network.core.Http;
import net.bensdeals.network.core.RemoteTask;
import roboguice.util.Strings;

import java.io.IOException;
import java.util.List;
@Singleton
public class TestRemoteTask extends RemoteTask {
    List<Pair<ApiRequest, ApiResponseCallbacks>> requests = Lists.newArrayList();

    @Inject
    public TestRemoteTask(Provider<Http> httpProvider) {
        super(httpProvider);
    }

    @Override
    public void makeRequest(ApiRequest apiRequest, ApiResponseCallbacks responseCallbacks) {
        requests.add(new Pair<ApiRequest, ApiResponseCallbacks>(apiRequest, responseCallbacks));
    }

    public boolean hasPendingRequests(){
        return !requests.isEmpty();
    }

    public ApiRequest getLatestRequest() {
        return requests.get(requests.size() - 1).first;
    }

    public <T> void simulateResponse(String responseFilename) throws IOException {
        verifyPendingRequest();
        if(Strings.isEmpty(responseFilename)) {
            dispatchFailedResponse();
        } else {
            dispatchSuccessResponse(new ApiResponse(200, StringUtil.responseAsStream(responseFilename)));
        }
    }

    private <T> void dispatchSuccessResponse(ApiResponse apiResponse) {
        requests.remove(0).second.dispatch(apiResponse);
    }

    private void dispatchFailedResponse() {
        requests.remove(0).second.dispatch(new ApiResponse(500, null));
    }

    private void verifyPendingRequest() {
        if(requests.isEmpty()) throw new RuntimeException("No Pending Request");
    }
}
