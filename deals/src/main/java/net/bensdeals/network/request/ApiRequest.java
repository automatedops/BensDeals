package net.bensdeals.network.request;

import java.util.Map;

import com.google.common.collect.Maps;

public abstract class ApiRequest<T> {

    public abstract String getUrl();
    public abstract Class<T> getResponseClass();

    public Map<String, String> getHeaders(){
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Accept-Encoding", "gzip,deflate");
        return headers;
    }
}
