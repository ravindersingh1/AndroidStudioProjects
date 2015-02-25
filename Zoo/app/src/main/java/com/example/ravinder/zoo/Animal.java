package com.example.ravinder.zoo;

import android.media.Image;

/**
 * Created by Ravinder on 1/24/15.
 */
public class Animal {

    //attributes
    Integer imageId;
    String name;
    String details;


    //Only constructor taking three parameters
    public Animal(Integer imageId, String name,String detail) {
        this.imageId = imageId;
        this.name = name;
        this.details = detail;
    }
    //Getters and setter methods follow
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

     public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

