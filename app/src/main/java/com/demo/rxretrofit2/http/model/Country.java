package com.demo.rxretrofit2.http.model;



public class Country {

    private String name;
    private String url;

    public Country() {}

    public Country(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
