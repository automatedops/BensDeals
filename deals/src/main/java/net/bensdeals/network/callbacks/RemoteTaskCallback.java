package net.bensdeals.network.callbacks;

import java.util.List;

import net.bensdeals.model.Deal;

public interface RemoteTaskCallback {

    public void onTaskSuccess(List<Deal> list);

    public void onTaskFailed();

    public void onTaskComplete();
}
