package net.bensdeals.utils;

import android.content.Context;
import com.flurry.android.FlurryAgent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.bensdeals.R;

@Singleton
public class Reporter {
    public static final String ON_APP_START = "ON_APP_START";

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

    public void report(String reportString) {
        if (Config.LOGGING)
            FlurryAgent.logEvent(reportString);
    }
}
