package net.bensdeals.network.core;

import net.bensdeals.model.Deal;

import java.util.List;

public class RemoteTaskCallback {
    public void onTaskSuccess(List<Deal> list){}
    public void onTaskSuccess(Response response){}
    public void onTaskFailed(){}
    public void onTaskComplete(){}
}
