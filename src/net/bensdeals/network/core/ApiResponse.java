package net.bensdeals.network.core;

import net.bensdeals.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unused")
public class ApiResponse {
    private int httpResponseCode;
    private final InputStream responseStream;

    public ApiResponse(int httpCode, InputStream responseStream) {
        this.httpResponseCode = httpCode;
        this.responseStream = responseStream;
    }

    public int getResponseCode() {
        return httpResponseCode;
    }

    public InputStream getResponseStream() {
        return responseStream;
    }

    public String getResponseBody() throws IOException {
        return StringUtil.fromStream(responseStream);
    }

    public boolean isSuccess() {
        return httpResponseCode >= 200 && httpResponseCode < 300;
    }

    public boolean isUnauthorized() {
        return httpResponseCode == 401;
    }

    @Override
    public String toString() {
        return "ApiResponse{ httpResponseCode=" + httpResponseCode + " }";
    }
}
