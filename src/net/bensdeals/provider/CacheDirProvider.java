package net.bensdeals.provider;

import android.content.Context;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.io.File;

public class CacheDirProvider implements Provider<File> {
    private Context context;
    @Inject
    public CacheDirProvider(Context context) {
        this.context = context;
    }

    @Override
    public File get() {
        return context.getCacheDir();
    }
}
