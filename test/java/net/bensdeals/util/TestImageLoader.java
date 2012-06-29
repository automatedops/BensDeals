package net.bensdeals.util;

import android.content.Context;
import android.widget.ImageView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.internal.Lists;
import com.google.inject.internal.Maps;
import com.google.inject.name.Named;
import net.bensdeals.network.ImageLoader;
import net.bensdeals.network.core.UrlStreamer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
@Singleton
public class TestImageLoader extends ImageLoader {
    List<ImageView> imageViewList = Lists.newArrayList();
    Map<ImageView, String> imageUrlMap = Maps.newHashMap();
    private boolean cancelOutstandingRequestsWasCalled;

    @Inject
    public TestImageLoader(@Named("download") ExecutorService downloadExecutor, @Named("draw") ExecutorService drawExecutor, Context context, UrlStreamer urlStreamer) {
        super(downloadExecutor, drawExecutor, context, urlStreamer);
    }

    @Override
    public void loadImage(ImageView imageView, String url) {
        imageViewList.add(imageView);
        imageUrlMap.put(imageView, url);
    }

    public String loadedImageUrl(ImageView imageView) {
        return imageUrlMap.get(imageView);
    }

    @Override
    public void cancelOutstandingRequests() {
        cancelOutstandingRequestsWasCalled = true;
    }

    public boolean cancelOutstandingRequestsWasCalled() {
        return cancelOutstandingRequestsWasCalled;
    }
}
