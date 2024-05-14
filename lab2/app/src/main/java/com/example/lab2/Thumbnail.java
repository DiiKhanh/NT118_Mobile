package com.example.lab2;

public enum Thumbnail {
    Thumbnail1("Thumnail 1", R.drawable.first_thumbnail),
    Thumbnail2("Thumnail 2", R.drawable.second_thumbnail),
    Thumbnail3("Thumnail 3", R.drawable.third_thumbnail),
    Thumbnail4("Thumnail 4", R.drawable.fourth_thumbnail);

    private String name;
    private int img;

    Thumbnail() {

    }

    Thumbnail(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}