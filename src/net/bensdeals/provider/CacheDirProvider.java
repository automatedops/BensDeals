package net.bensdeals.provider;

import android.content.Context;
import android.os.AsyncTask;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.bensdeals.utils.DateFormatter;

import java.io.File;
import java.io.FileFilter;

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

    public void clear() {
        final File dir = get();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                final long current = System.currentTimeMillis();
                try {
                    File[] files = dir.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File file) {
                            boolean isFile = file.isFile();
                            boolean shouldDelete = current - file.lastModified() > DateFormatter.DAY_IN_MILLI * 14;
                            return shouldDelete && isFile;
                        }
                    });
                    for (File file : files) file.delete();
                } catch (Exception ignored){
                }
                return null;
            }
        }.execute((Void) null);
    }
}
