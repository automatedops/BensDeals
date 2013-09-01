package net.bensdeals.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

public class SearchItem implements Serializable {
    @SerializedName("link") String link;
    @SerializedName("brand") String brand;
    @SerializedName("title") String title;
    @SerializedName("description") String description;
    @SerializedName("images") List<Image> images = Lists.newArrayList();
    @SerializedName("inventories") List<Prices> prices = Lists.newArrayList();
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Prices> getPrices() {
        return prices;
    }

    public void setPrices(List<Prices> prices) {
        this.prices = prices;
    }

    public String printPrice() {
        if(prices.isEmpty()) return "";
        Prices price = prices.get(0);
        return "$ " + price.getPrice() + (price.isFreeShipping() ? " Free shipping " : " + shipping ");
    }

    public static class Image{
        @SerializedName("link") String link = "";
        @SerializedName("status") String status;
        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Prices{
        @SerializedName("price") float price;
        @SerializedName("shipping") float shipping;
        @SerializedName("availability") String availability;

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getShipping() {
            return shipping;
        }

        public void setShipping(float shipping) {
            this.shipping = shipping;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public boolean isFreeShipping(){
            return shipping == 0.0d;
        }
    }
}
