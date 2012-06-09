package net.bensdeals.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class LayoutInflaterWithInjection<T extends View> {

    private Injector injector;
    private Context context;

    @Inject
    public LayoutInflaterWithInjection(Injector injector, Context context) {
        this.injector = injector;
        this.context = context;
    }

    public T inflate(int resourceId) {
        T view = (T) LayoutInflater.from(context).inflate(resourceId, null, false);
        return injectRecursively(view);
    }

    private <T extends View> T injectRecursively(T view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                injectRecursively(viewGroup.getChildAt(i));
            }
        }
        injector.injectMembers(view);
        return view;
    }
}
