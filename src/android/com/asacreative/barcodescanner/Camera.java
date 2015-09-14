package com.asacreative.barcodescanner;

import android.util.Log;

public class Camera {
    public static final String TAG = "BarcodeCamera";

    private int x;
    private int y;
    private int width;
    private int height;

    public Camera(int x, int y, int width, int height) {
        Log.v(TAG, "Constructor");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Camera() {
        Log.v(TAG, "Constructor");
    }

    public void setDimension(int x, int y, int width, int height) {
        Log.v(TAG, "Set Dimension" + x + y + width + height);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        return;
    }

    public void release() {
        Log.v(TAG, "Set Dimension" + x + y + width + height);

        return;
    }

}