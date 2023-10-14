package br.com.collec.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movies {

    @JsonProperty("url")
    private String url;

    public Movies(String url) {
        this.url = url;
    }

    public Movies() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
