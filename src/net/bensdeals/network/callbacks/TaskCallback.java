package net.bensdeals.network.callbacks;

public class TaskCallback<T> {
    public void onTaskSuccess(T t) { }
    public void onTaskFailed() { }
    public void onTaskComplete() { }
}
