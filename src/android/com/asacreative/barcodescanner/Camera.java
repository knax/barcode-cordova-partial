package com.asacreative.barcodescanner;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

public class Camera {
    public static final String TAG = "BarcodeCamera";
    private final CordovaInterface cordova;

    private int x;
    private int y;
    private int width;
    private int height;

    public boolean isCameraStarted = false;

    private int textViewId = 111;
    private int containerViewId = 1;
    private CameraViewfinderFragment cameraFragment;

    public Camera(CordovaInterface cordova, int x, int y, int width, int height) {
        Log.v(TAG, "Constructor");

        this.cordova = cordova;
        this.setDimension(x, y, width, height);

    }

    public Camera(CordovaInterface cordova) {
        this(cordova, 0, 0, 0, 0);
    }

    public void setDimension(int x, int y, int width, int height) {
        Log.v(TAG, "Set Dimension" + x + y + width + height);

        DisplayMetrics metrics = cordova.getActivity().getResources().getDisplayMetrics();

        this.x = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, metrics);
        this.y = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, metrics);
        this.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, metrics);
        this.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, metrics);

    }

    public void startPreview() {
        Log.v(TAG, "On start preview");
        if (cameraFragment != null) {
            return;
        }
        cameraFragment = new CameraViewfinderFragment();

        cameraFragment.setDimension(x,y,width,height);

        cordova.getActivity().runOnUiThread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void run() {

                try {

                    //create or update the layout params for the container view
                    FrameLayout containerView = (FrameLayout) cordova.getActivity().findViewById(containerViewId);
                    if (containerView == null) {
                        containerView = new FrameLayout(cordova.getActivity().getApplicationContext());
                        containerView.setId(containerViewId);

                        FrameLayout.LayoutParams containerLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                        cordova.getActivity().addContentView(containerView, containerLayoutParams);
                    }


                    containerView.bringToFront();

                    //add the fragment to the container
                    FragmentManager fragmentManager = cordova.getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(containerView.getId(), cameraFragment);
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.isCameraStarted = true;

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void stopPreview() {
        if (cameraFragment == null) {
            return;
        }

        FragmentManager fragmentManager = cordova.getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(cameraFragment);
        fragmentTransaction.commit();
        cameraFragment = null;

        this.isCameraStarted = false;
    }

    public void release() {
        Log.v(TAG, "Release");

    }

    public String toString() {
        return String.format("Camera Viewfinder with x:%d, y:%d, width:%d, height:%d", this.x, this.y, this.width, this.height);
    }


}