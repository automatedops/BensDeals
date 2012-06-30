package net.bensdeals.network.core;

import net.bensdeals.model.Deal;

import java.util.List;

public interface RemoteTaskCallback {
    void onTaskSuccess(List<Deal> list);
    void onTaskFailed();
    void onTaskComplete();
}
