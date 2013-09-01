package net.bensdeals.model.wrapper;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import net.bensdeals.model.SearchItem;

public class SearchResponseWrapper {
    @SerializedName("nextLink") String nextLink;
    @SerializedName("items") List<SearchItemWrapper> items = Lists.newArrayList();

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
        public @SerializedName("product") SearchItem item = new SearchItem();

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
