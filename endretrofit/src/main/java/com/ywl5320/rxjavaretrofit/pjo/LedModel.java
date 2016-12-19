package com.ywl5320.rxjavaretrofit.pjo;

/**
 * Created by liuwen on 2016/12/15.
 */
public class LedModel {

    private int id;
    private String name;
    private int imageUrl;

    private int liang;
    private int an;

    public LedModel(int id, String name, int imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public LedModel(int id, String name, int imageUrl, int liang, int an) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.liang = liang;
        this.an = an;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLiang() {
        return liang;
    }

    public void setLiang(int liang) {
        this.liang = liang;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }
}
