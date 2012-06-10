package net.bensdeals.network.core;

import com.google.inject.Inject;
import net.bensdeals.provider.HttpClientProvider;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import roboguice.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

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
        private HttpResponse httpResponse;

        public Response(HttpResponse httpResponse) {
            this.httpResponse = httpResponse;
        }

        public boolean isGzipResponse() {
            Header firstHeader = httpResponse.getFirstHeader("Content-Encoding");
            if(firstHeader == null) return false;
            String name = firstHeader.getValue();
            return !Strings.isEmpty(name) && name.contains("gzip");
        }

        public int getStatusCode() {
            return httpResponse.getStatusLine().getStatusCode();
        }

        public InputStream getResponseStream() throws IOException {
            return httpResponse.getEntity().getContent();
        }

        public InputStream getZipppedResponseStream() throws IOException {
            return new GZIPInputStream(httpResponse.getEntity().getContent());
        }
    }
}