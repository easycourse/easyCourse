package com.easyCourse.entity;

/**
 * @author SPG
 * @version 1.0
 * @time 2019-05-09-15:58
 */
public class OSSFile {

    private String name;
    private String size;
    private String url;
    private String lastModified;

    public OSSFile(String name, String size, String url, String lastModified) {
        this.name = name;
        this.size = size;
        this.url = url;
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
