package net.bensdeals.utils;

import javax.inject.Inject;
import java.util.Map;

import android.content.Context;
import android.util.Pair;

import com.flurry.android.FlurryAgent;
import com.google.common.collect.Maps;
import net.bensdeals.R;

public class Reporter {
    public static final String ON_APP_START = "ON_APP_START";
    public static final String ON_SEARCH = "ON_SEARCH";
    public static final String ON_SEARCH_VIEW = "ON_SEARCH_VIEW";
    public static final String KEY_SEARCH_KEYWORDS = "KEY_SEARCH_KEYWORDS";

    @Inject
    public Reporter() {
    }


    public void startSession(Context context) {
        if (Config.LOGGING)
            FlurryAgent.onStartSession(context, context.getString(R.string.flurry_key));
    }

    public void endSession(Context context) {
        if (Config.LOGGING)
            FlurryAgent.onEndSession(context);
    }

    public void report(String eventName) {
        if (Config.LOGGING)
            FlurryAgent.logEvent(eventName);
    }

    public void report(String eventName, Pair<String, String>... pairs) {
        if (Config.LOGGING) {
            Map<String, String> maps = Maps.newHashMap();
            for (Pair<String, String> pair : pairs) maps.put(pair.first, pair.second);
            FlurryAgent.logEvent(eventName, maps);
        }
    }
}
