package net.bensdeals.network.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class Response<T> {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    private final int statusCode;
    private final Class<T> responseClass;
    private final InputStream inputStream;
    private T entity;

    public Response(int statusCode, InputStream inputStream, Class<T> responseClass) {
        this.responseClass = responseClass;
        this.inputStream = inputStream;
        this.statusCode = statusCode;
    }

    public T getEntity() {
        return entity;
    }

    public boolean isSuccessResponse() {
        return statusCode >= 200 && statusCode < 300;
    }

    public void assignEntity() {
        try {
            entity = OBJECT_MAPPER.readValue(inputStream, responseClass);
        } catch (IOException ignored) {
        }
    }
}
