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
    
    public static class SearchItemWrapper implements Comparable{
        public @JsonProperty("product") SearchItem item = new SearchItem();

        @Override
        public int compareTo(Object o) {
            List<SearchItem.Prices> itemPrices = item.getPrices();
            List<SearchItem.Prices> searchItemPrices = ((SearchItemWrapper) o).item.getPrices();
            return itemPrices.isEmpty() ? -1 : searchItemPrices.isEmpty() ? -1 : (int)(getPrice(itemPrices) - getPrice(searchItemPrices));
        }

        private float getPrice(List<SearchItem.Prices> prices) {
            SearchItem.Prices price = prices.get(0);
            return price.getPrice() + price.getShipping();
        }
    }
}
