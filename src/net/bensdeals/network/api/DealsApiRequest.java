package net.bensdeals.network.api;

import net.bensdeals.network.core.ApiRequest;

public class DealsApiRequest extends ApiRequest{
    @Override
    public String getUrlString() {
        return "http://bensbargains.net/rss/";
    }
}
