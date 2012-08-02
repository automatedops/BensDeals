package net.bensdeals.network.response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bensdeals.utils.ALog;
import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class Response<T> {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    private final int statusCode;
    private final Class<T> responseClass;
    private final InputStream inputStream;
    private T entity;

    public Response(int statusCode, InputStream inputStream, Header[] headers, Class<T> responseClass) {
        this.responseClass = responseClass;
        this.statusCode = statusCode;
        this.inputStream = ensureZippedStream(headers, inputStream);
    }

    private InputStream ensureZippedStream(Header[] headers, InputStream inputStream) {
        if(headers == null || headers.length == 0) return inputStream;
        for (Header header : headers) {
            if ("gzip".equals(header.getValue())) {
                try {
                    return new GZIPInputStream(inputStream);
                } catch (IOException e) {
                    ALog.e(e);
                    return null;
                }
            }
        }
        return inputStream;
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
        } catch (IOException e) {
            ALog.e(e);
        }
    }
}
