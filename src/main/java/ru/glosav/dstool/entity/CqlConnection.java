package ru.glosav.dstool.entity;

import static ru.glosav.dstool.utils.AnyUtils.isEmpty;

/**
 * Created by abalyshev on 14.04.17.
 */
public class CqlConnection extends Connection {
    private String keySpace;
    public CqlConnection(String url) {
        this(url, null);
    }
    public CqlConnection(String url, String keySpace) {
        super(url);
        this.keySpace = keySpace;
    }
    public String getKeySpace() { return keySpace; }
    public void setKeySpace(String keySpace) { this.keySpace = keySpace; }
    public boolean hasKeySpace() { return !isEmpty(keySpace); }
    @Override
    public String toString() {
        return String.format("[%s:%s]", getUrl(), getKeySpace());
    }
}
