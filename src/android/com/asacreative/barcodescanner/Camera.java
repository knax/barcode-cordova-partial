package com.asacreative.barcodescanner;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

public class Camera {
    public static final String TAG = "BarcodeCamera";

    private int x;
    private int y;
    private int width;
    private int height;

    public boolean isCameraStarted = false;

    private CordovaInterface cordova;
    private CordovaWebView webView;

    private int textViewId = 111;

    public Camera(CordovaInterface cordova, CordovaWebView webView, int x, int y, int width, int height) {
        Log.v(TAG, "Constructor");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.cordova = cordova;
        this.webView = webView;

    }

    public Camera(CordovaInterface cordova, CordovaWebView webView) {
        this(cordova, webView, 0, 0, 0, 0);
    }

    public void setDimension(int x, int y, int width, int height) {
        Log.v(TAG, "Set Dimension" + x + y + width + height);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public void startPreview() {

        Runnable runnable = new Runnable() {
            public void run() {
                FrameLayout layout = (FrameLayout) webView.getView().getParent();

                TextView textView = new TextView(layout.getContext());
                textView.setBackgroundColor(Color.BLUE);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
                params.setMargins(x, y, 0, 0);
                textView.setLayoutParams(params);
                textView.setId(textViewId);
                textView.setText("Hello World");
                layout.addView(textView);
            }
        };
        this.cordova.getActivity().runOnUiThread(runnable);
        this.isCameraStarted = true;

    }

    public void stopPreview() {
        Runnable runnable = new Runnable() {
            public void run() {
                FrameLayout layout = (FrameLayout) webView.getView().getParent();

                View textView = layout.findViewById(textViewId);

                ((ViewGroup) textView.getParent()).removeView(textView);
            }
        };
        this.cordova.getActivity().runOnUiThread(runnable);

        this.isCameraStarted = false;
    }

    public void release() {
        Log.v(TAG, "Release");

    }

    public String toString() {
        return String.format("Camera Viewfinder with x:%d, y:%d, width:%d, height:%d", this.x, this.y, this.width, this.height);
    }


}