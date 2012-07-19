package net.bensdeals.network;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.internal.Maps;
import com.google.inject.name.Named;
import net.bensdeals.network.core.UrlStreamer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static android.os.Process.THREAD_PRIORITY_DEFAULT;
import static android.os.Process.THREAD_PRIORITY_DISPLAY;
import static android.os.Process.setThreadPriority;

@Singleton
public class ImageLoader {
    private ExecutorService downloadExecutor;
    private ExecutorService drawExecutor;
    final private Map<String, Future<Drawable>> outstandingDownloads = Maps.newHashMap();
    final private Map<ImageView, Future<?>> outstandingDraws = Maps.newHashMap();
    private UrlStreamer urlStreamer;
    private Looper mainLooper;

    @Inject
    public ImageLoader(
            @Named("download") ExecutorService downloadExecutor,
            @Named("draw") ExecutorService drawExecutor,
            Context context,
            UrlStreamer urlStreamer) {
        this.downloadExecutor = downloadExecutor;
        this.drawExecutor = drawExecutor;
        this.urlStreamer = urlStreamer;
        mainLooper = context.getMainLooper();
    }

    public void loadImage(ImageView imageView, String url) {
        imageView.setImageDrawable(null);
        imageView.setTag(url);
        downloadAndDrawImage(imageView, url);
    }

    private void downloadAndDrawImage(ImageView imageView, String url) {
        final Future<Drawable> drawableFuture;
        synchronized (outstandingDownloads) {
            Future<Drawable> tempDrawableFuture = outstandingDownloads.get(url);
            if (tempDrawableFuture == null) {
                tempDrawableFuture = downloadExecutor.submit(new DownloadCallable(url, urlStreamer));
                outstandingDownloads.put(url, tempDrawableFuture);
            }
            drawableFuture = tempDrawableFuture;

            Future<?> drawFuture = drawExecutor.submit(new DrawRunnable(drawableFuture, url, imageView, mainLooper));
            outstandingDraws.put(imageView, drawFuture);
        }
    }

    private class DownloadCallable implements Callable<Drawable> {
        private final String imageUrl;
        private UrlStreamer urlStreamer;

        public DownloadCallable(String imageUrl, UrlStreamer urlStreamer) {
            this.imageUrl = imageUrl;
            this.urlStreamer = urlStreamer;
        }

        @Override
        public Drawable call() throws Exception {
            InputStream inputStream = null;
            try {
                setThreadPriority(THREAD_PRIORITY_DEFAULT);
                inputStream = urlStreamer.get(imageUrl);
                return Drawable.createFromStream(inputStream, "src");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
    }

    private class DrawRunnable implements Callable<Void> {
        private final Future<Drawable> drawableFuture;
        private final String imageUrl;
        private final ImageView imageView;
        private final Looper mainLooper;

        public DrawRunnable(Future<Drawable> drawableFuture, String imageUrl, ImageView imageView, Looper mainLooper) {
            this.drawableFuture = drawableFuture;
            this.imageUrl = imageUrl;
            this.imageView = imageView;
            this.mainLooper = mainLooper;
        }


        public Void call() {
            try {
                setThreadPriority(THREAD_PRIORITY_DISPLAY);
                final Drawable drawable = drawableFuture.get();
                if (imageUrl.equals(imageView.getTag())) {
                    new Handler(mainLooper).post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageDrawable(drawable);
                            imageView.setTag(null);
                        }
                    });
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } finally {
                synchronized (outstandingDownloads) {
                    outstandingDownloads.remove(imageUrl);
                    outstandingDraws.remove(imageView);
                }
            }
            return null;
        }
    }

    public void cancelOutstandingRequests() {
        synchronized (outstandingDownloads) {
            for (Future<Drawable> downloadFuture : outstandingDownloads.values()) {
                downloadFuture.cancel(true);
            }
            outstandingDownloads.clear();

            for (Future<?> drawFuture : outstandingDraws.values()) {
                drawFuture.cancel(true);
            }
            outstandingDraws.clear();
        }
    }
}
