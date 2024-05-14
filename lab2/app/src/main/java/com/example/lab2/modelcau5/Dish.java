package com.example.lab2.modelcau5;

import com.example.lab2.Thumbnail;

public class Dish {
    private String dishName;
    private Thumbnail thumbnail;
    private boolean isPromotion;

    public Dish() {
    }

    public Dish(String dishName, Thumbnail thumbnail, boolean isPromotion) {
        this.dishName = dishName;
        this.thumbnail = thumbnail;
        this.isPromotion = isPromotion;
    }

    public Dish(String dishName, Thumbnail thumbnail) {
        this.dishName = dishName;
        this.thumbnail = thumbnail;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }
}