package net.bensdeals.provider;

import android.content.Context;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.io.File;

/**
* Created with IntelliJ IDEA.
* User: Wei
* Date: 12-4-18
* Time: 下午10:53
* To change this template use File | Settings | File Templates.
*/
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
