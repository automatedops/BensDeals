package net.bensdeals.network.callbacks;

import net.bensdeals.network.core.ApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Wei
 * Date: 03/29/12
 * Time: 9:38
 */
public class ApiResponseCallbacks {
    public void onStart() {}
    public void onSuccess(ApiResponse response){}
    public void onFailure(){}
    public void onComplete(){}
    public void onError(){}

    public void dispatch(ApiResponse apiResponse) {
        if (apiResponse.isSuccess()) {
            onSuccess(apiResponse);
        } else {
            onFailure();
        }
        onComplete();
    }
}
