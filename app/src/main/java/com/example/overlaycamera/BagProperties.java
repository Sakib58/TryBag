package com.example.overlaycamera;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class BagProperties {
    public Bitmap side1;
    public Bitmap side2;
    public Bitmap side3;
    public String color;
    public String name;
    public String site;
    public String docId;
    private boolean isChildLiked,isAdultLiked;

    public boolean isChildLiked() {
        return isChildLiked;
    }

    public void setChildLiked(boolean childLiked) {
        isChildLiked = childLiked;
    }

    public boolean isAdultLiked() {
        return isAdultLiked;
    }

    public void setAdultLiked(boolean adultLiked) {
        isAdultLiked = adultLiked;
    }

    public List<Bitmap> sides = new ArrayList<>();

    public Bitmap getSide1() {
        return side1;
    }

    public void setSide1(Bitmap side1) {
        this.side1 = side1;
    }

    public Bitmap getSide2() {
        return side2;
    }

    public void setSide2(Bitmap side2) {
        this.side2 = side2;
    }

    public Bitmap getSide3() {
        return side3;
    }

    public void setSide3(Bitmap side3) {
        this.side3 = side3;
    }

    public BagProperties(String docId,String color, String name, String site) {
        this.docId = docId;
        this.color = color;
        this.name = name;
        this.site = site;
        isAdultLiked = false;
        isChildLiked = false;

        sides.add(side1);
        sides.add(side2);
        sides.add(side3);
    }
}
