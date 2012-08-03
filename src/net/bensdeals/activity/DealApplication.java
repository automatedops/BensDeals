package net.bensdeals.activity;

import com.crittercism.app.Crittercism;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import net.bensdeals.R;
import net.bensdeals.provider.CacheDirProvider;
import net.bensdeals.provider.HttpClientProvider;
import net.bensdeals.utils.Config;
import org.apache.http.impl.client.DefaultHttpClient;
import roboguice.application.RoboApplication;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.google.inject.Scopes.SINGLETON;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class DealApplication extends RoboApplication {
    private Module module = new AppModules();

    @Override
    protected void addApplicationModules(List<Module> modules) {
        modules.add(module);
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Config.LOGGING) Crittercism.init(getApplicationContext(), getString(R.string.crittercism_key));
    }

    private class AppModules implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(File.class).annotatedWith(Names.named("cacheDir")).toProvider(CacheDirProvider.class);
            binder.bind(ExecutorService.class).annotatedWith(Names.named("download")).toInstance(newFixedThreadPool(3));
            binder.bind(ExecutorService.class).annotatedWith(Names.named("draw")).toInstance(newFixedThreadPool(2));
            binder.bind(DefaultHttpClient.class).toProvider(new HttpClientProvider()).in(SINGLETON);
        }
    }
}
