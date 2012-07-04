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
    public Reporter() { }


    public void startSession(Context context) {
        FlurryAgent.onStartSession(context, context.getString(R.string.flurry_id));
    }

    public void endSession(Context context) {
        FlurryAgent.onEndSession(context);
    }

    public void report(String reportString) {
        FlurryAgent.logEvent(reportString);
    }
}
