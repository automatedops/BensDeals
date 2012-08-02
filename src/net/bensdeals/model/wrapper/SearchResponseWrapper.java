package net.bensdeals.model.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.internal.Lists;
import net.bensdeals.model.SearchItem;

import java.util.List;

public class SearchResponseWrapper {
    @JsonProperty("nextLink") String nextLink;
    @JsonProperty("items") List<SearchItemWrapper> items = Lists.newArrayList();

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public List<SearchItemWrapper> getItems() {
        return items;
    }

    public void setItems(List<SearchItemWrapper> items) {
        this.items = items;
    }
    
    public static class SearchItemWrapper {
        public @JsonProperty("product") SearchItem item = new SearchItem();
    }
}
