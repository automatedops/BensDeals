package net.bensdeals.network.core;

import com.google.inject.Inject;
import net.bensdeals.provider.HttpClientProvider;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.11.1
 * User: Wei W.
 * Date: 03/29/2012
 * Time: 21:49
 */
public class Http {
    private HttpClientProvider clientProvider;

    @Inject
    public Http(HttpClientProvider clientProvider) {
        this.clientProvider = clientProvider;
    }

    public Response get(ApiRequest apiRequest) throws IOException, URISyntaxException {
        return makeRequest(apiRequest.getHeaders(), new HttpGet(new URI(apiRequest.getUrlString())));
    }

    public Response post(ApiRequest apiRequest) throws IOException, URISyntaxException {
        HttpPost method = new HttpPost(new URI(apiRequest.getUrlString()));
        method.setEntity(new StringEntity(apiRequest.getPostBody(), "UTF-8"));
        return makeRequest(apiRequest.getHeaders(), method);
    }

    private Response makeRequest(Map<String, String> headers, HttpRequestBase method) {
        HttpClient client = clientProvider.get();
        try {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                method.setHeader(entry.getKey(), entry.getValue());
            }
            return new Response(client.execute(method));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (client != null) {
                try {
                    client.getConnectionManager().shutdown();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static class Response {
        private static final int BUFFER_SIZE = 4096;
        private int statusCode;
        private String responseBody;

        public Response(HttpResponse httpResponse) {
            statusCode = httpResponse.getStatusLine().getStatusCode();
            try {
                responseBody = fromStream(httpResponse.getEntity().getContent());
            } catch (IOException e) {
                throw new RuntimeException("error reading response body", e);
            }
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public String fromStream(InputStream inputStream) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), BUFFER_SIZE);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } finally {
                inputStream.close();
            }
            return stringBuilder.toString();
        }
    }
}