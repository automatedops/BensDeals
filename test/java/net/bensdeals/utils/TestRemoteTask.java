package net.bensdeals.utils;

import android.util.Pair;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.internal.Lists;
import net.bensdeals.network.core.RemoteTask;
import net.bensdeals.network.core.RemoteTaskCallback;

import java.util.List;

@Singleton
public class TestRemoteTask extends RemoteTask {
    List<Pair<String, RemoteTaskCallback>> requests = Lists.newArrayList();
    @Inject
    public TestRemoteTask() {
        super(null);
    }

    @Override
    public void makeRequest(String path, RemoteTaskCallback callback) {
        requests.add(new Pair<String, RemoteTaskCallback>(path, callback));
    }

    public boolean hasPendingRequests(){
        return !requests.isEmpty();
    }

    public String getLatestRequestPath() {
        return requests.get(requests.size()-1).first;
    }

    public void simulateFailedResponse() {
        Pair<String, RemoteTaskCallback> remove = requests.remove(0);
        remove.second.onTaskFailed();
    }
}
