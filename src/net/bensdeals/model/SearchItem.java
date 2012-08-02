package net.bensdeals.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.internal.Lists;

import java.io.Serializable;
import java.util.List;

public class SearchItem implements Serializable {
    @JsonProperty("link") String link;
    @JsonProperty("brand") String brand;
    @JsonProperty("title") String title;
    @JsonProperty("description") String description;
    @JsonProperty("images") List<Image> images = Lists.newArrayList();
    @JsonProperty("inventories") List<Prices> prices = Lists.newArrayList();
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
        @JsonProperty("link") String link = "";
        @JsonProperty("status") String status;
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
        @JsonProperty("price") float price;
        @JsonProperty("shipping") float shipping;
        @JsonProperty("availability") String availability;

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

        @JsonIgnore
        public boolean isFreeShipping(){
            return shipping == 0.0d;
        }
    }
}
