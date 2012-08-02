package net.bensdeals.network.callbacks;

import net.bensdeals.model.Deal;

import java.util.List;

public class RemoteTaskCallback {
    public void onTaskSuccess(List<Deal> list){}
    public void onTaskFailed(){}
    public void onTaskComplete(){}
}
