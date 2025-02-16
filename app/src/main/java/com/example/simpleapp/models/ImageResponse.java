package com.example.simpleapp.models;

import java.util.List;

public class ImageResponse {
    private List<Image> hits;
    private int totalHits;

    public List<Image> getHits() {
        return hits;
    }

    public void setHits(List<Image> hits) {
        this.hits = hits;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }
}
