package ru.glosav.dstool.entity;

/**
 * Created by abalyshev on 14.04.17.
 */
public class Connection {
    private String url;
    public Connection(String url) {
        this.url = url;
    }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
