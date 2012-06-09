package net.bensdeals.network.core;

import com.google.inject.internal.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.11.1
 * User: Wei W.
 * Date: 03/29/2012
 * Time: 21:44
 */
public abstract class ApiRequest {
    public abstract String getUrlString();

    public Map<String, String> getHeaders() {
        HashMap<String, String> hashMap = Maps.newHashMap();
        hashMap.put("Accept", "*/*");
        hashMap.put("Accept-Encoding", "gzip, deflate");
        return hashMap;
    }

    public String getPostBody() {
        return null;
    }

}