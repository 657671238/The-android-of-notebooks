package com.example.my_notebooks;

public class dats {
    private String des; // 水果名称
    private String main; // 水果图片id值
    private String time;
    public dats(String name, String main,String time) {
        this.des = name;
        this.main = main;
        this.time = time;
    }
    public String getName() {
        return des;
    }
    public void setName(String name) {
        this.des = name;
    }
    public String getImageId() {
        return main;
    }
    public void setImageId(String imageId) {
        this.main = imageId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
