package net.bensdeals.utils;

import android.util.Pair;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.internal.Lists;
import net.bensdeals.model.Deal;
import net.bensdeals.network.callbacks.RemoteTaskCallback;
import net.bensdeals.network.callbacks.TaskCallback;
import net.bensdeals.network.core.RemoteTask;
import net.bensdeals.network.request.ApiRequest;

import java.util.List;

import static net.bensdeals.network.response.Response.OBJECT_MAPPER;

@Singleton
public class TestRemoteTask extends RemoteTask {
    List<Pair<String, RemoteTaskCallback>> dealRequests = Lists.newArrayList();
    private List<Pair<ApiRequest, TaskCallback>> requests = Lists.newArrayList();

    @Inject
    public TestRemoteTask() {
        super(null);
    }

    @Override
    public void makeRequest(String path, RemoteTaskCallback callback) {
        dealRequests.add(new Pair<String, RemoteTaskCallback>(path, callback));
    }

    @Override
    public <T> void makeRequest(ApiRequest<T> apiRequest, TaskCallback<T> callback) {
        requests.add(new Pair<ApiRequest, TaskCallback>(apiRequest, callback));
    }

    public boolean hasPendingDealRequests() {
        return !dealRequests.isEmpty();
    }

    public boolean hasPendingRequests() {
        return !requests.isEmpty();
    }

    public String getLatestDealRequestPath() {
        return dealRequests.get(dealRequests.size() - 1).first;
    }

    public ApiRequest getLatestApiRequest() {
        return requests.get(requests.size() - 1).first;
    }

    public void simulateFailedDealResponse() {
        Pair<String, RemoteTaskCallback> remove = dealRequests.remove(0);
        remove.second.onTaskFailed();
        remove.second.onTaskComplete();
    }

    public void simulateFailedResponse() {
        Pair<ApiRequest, TaskCallback> remove = requests.remove(0);
        remove.second.onTaskFailed();
        remove.second.onTaskComplete();
    }

    public void simulateSuccessDealResponse(String responseFileName) throws Exception {
        Pair<String, RemoteTaskCallback> remove = dealRequests.remove(0);
        remove.second.onTaskSuccess(Deal.parseXml(StringUtil.responseAsStream(responseFileName)));
        remove.second.onTaskComplete();
    }

    public void simulateSuccessResponse(String responseFileName) throws Exception {
        Pair<ApiRequest, TaskCallback> remove = requests.remove(0);
        remove.second.onTaskSuccess(OBJECT_MAPPER.readValue(StringUtil.responseAsStream(responseFileName), remove.first.getResponseClass()));
        remove.second.onTaskComplete();
    }
}
