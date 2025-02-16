package com.example.simpleapp.models;

public class Image {
    private String id;
    private String webformatURL; //

    public Image(String id, String webformatURL) {
        this.id = id;
        this.webformatURL = webformatURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

}
