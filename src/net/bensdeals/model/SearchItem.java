package net.bensdeals.model;

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
}
