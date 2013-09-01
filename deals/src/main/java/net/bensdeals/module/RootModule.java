package net.bensdeals.module;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import net.bensdeals.activity.DealApplication;
import net.bensdeals.activity.DealPagerActivity;
import net.bensdeals.adapter.DealListAdapter;
import net.bensdeals.network.RemoteTask;
import net.bensdeals.provider.CacheDirProvider;
import net.bensdeals.provider.CurrentDateProvider;
import net.bensdeals.provider.XMLPathProvider;
import net.bensdeals.utils.Reporter;
import net.bensdeals.views.DealItemView;

@Module(
        complete = true,
        library = true,
        injects = {
                DealApplication.class,

                DealPagerActivity.class,
                DealListAdapter.class,
                DealItemView.class
        }
)
public class RootModule {
    @Provides
    public Context provideContext() {
        return DealApplication.getContext();
    }

    @Provides
    public CacheDirProvider provideCacheDirProvider(Context context) {
        return new CacheDirProvider(context);
    }

    @Provides
    @Singleton
    @Named("download")
    public ExecutorService provideDownloadService() {
        return Executors.newFixedThreadPool(2);
    }

    @Provides
    public RemoteTask provideRemoteTask(OkHttpClient httpClient, @Named("download") ExecutorService executorService, @Named("main") Handler handler) {
        return new RemoteTask(httpClient, executorService, handler);
    }

    @Provides
    public OkHttpClient provideHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    public XMLPathProvider provideXMLPathProvider(Context context) {
        return new XMLPathProvider(context);
    }

    @Provides
    @Singleton
    public Reporter provideReporter() {
        return new Reporter();
    }

    @Provides
    public LayoutInflater provideReporter(Context context) {
        return LayoutInflater.from(context);
    }

    @Provides
    public CurrentDateProvider provideCurrentDate() {
        return new CurrentDateProvider();
    }

    @Provides
    @Singleton
    @Named("main")
    public Handler providerMainHandler() {
        return new Handler(Looper.getMainLooper());
    }

}
