package com.example.overlaycamera;

import android.graphics.Bitmap;

public class ImageProperties {
    Bitmap imageBitmap;
    int height,width,marginLeft,marginTop,section,side;

    public ImageProperties(Bitmap imageBitmap, int height, int width, int marginLeft, int marginTop, int section, int side) {
        this.imageBitmap = imageBitmap;
        this.height = height;
        this.width = width;
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.section = section;
        this.side = side;
    }
}
