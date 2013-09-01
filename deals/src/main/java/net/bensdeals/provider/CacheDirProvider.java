package net.bensdeals.provider;

import javax.inject.Inject;
import java.io.File;
import java.io.FileFilter;

import android.content.Context;
import android.os.AsyncTask;

import net.bensdeals.utils.DateFormatter;

public class CacheDirProvider {
    private Context context;

    public CacheDirProvider(Context context) {
        this.context = context;
    }

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
                } catch (Exception ignored) {
                }
                return null;
            }
        }.execute((Void) null);
    }
}
