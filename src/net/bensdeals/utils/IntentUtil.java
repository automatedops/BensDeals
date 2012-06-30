package net.bensdeals.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import net.bensdeals.model.Deal;

public class IntentUtil {
    public static void intentForShare(Context context, Deal deal) {
        try {
            Intent intent = new Intent();
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_SUBJECT, deal.getTitle());
            intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(deal.getDescription()));
            context.startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception ignored) {
        }
    }

    public static void intentForWeb(Context context, Deal deal) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(deal.getLink())));
        } catch (Exception ignored) {
        }
    }
}
