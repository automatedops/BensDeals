package net.bensdeals.activity;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;
import net.bensdeals.module.RootModule;

public class DealApplication extends Application {
    private static Context sContext;
    private ObjectGraph mObjectGraph;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (Config.LOGGING) Crittercism.initialize(getApplicationContext(), getString(R.string.crittercism_key));
        sContext = this;

        mObjectGraph = ObjectGraph.create(new RootModule());
    }

    public void inject(Object o) {
        mObjectGraph.inject(o);
    }
}
