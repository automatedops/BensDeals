package net.bensdeals.provider;

import com.google.inject.Provider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
* Created with IntelliJ IDEA.
* User: Wei
* Date: 12-4-1
* Time: 下午3:23
* To change this template use File | Settings | File Templates.
*/
public class HttpClientProvider implements Provider<HttpClient> {
    @Override
    public HttpClient get() {
        HttpParams parameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parameters, 20000);
        HttpConnectionParams.setSoTimeout(parameters, 20000);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        return new DefaultHttpClient(new ThreadSafeClientConnManager(parameters, schemeRegistry), parameters);
    }
}
