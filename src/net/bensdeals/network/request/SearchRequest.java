package net.bensdeals.network.request;

import net.bensdeals.model.wrapper.SearchResponseWrapper;

import java.net.URLEncoder;

public class SearchRequest extends ApiRequest<SearchResponseWrapper> {

    private final String key;
    private final String text;

    public SearchRequest(String key, String text) {
        this.key = key;
        this.text = text;
    }

    @Override
    public String getUrl() {
        return "https://www.googleapis.com/shopping/search/v1/public/products?key=" + key + "&country=US&q=" + URLEncoder.encode(text);
    }

    @Override
    public Class<SearchResponseWrapper> getResponseClass() {
        return SearchResponseWrapper.class;
    }
}
