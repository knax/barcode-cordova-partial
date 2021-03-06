package com.asacreative.barcodescanner;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
import android.provider.Settings;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.asacreative.barcodescanner.Camera;
import org.apache.cordova.PluginResult;

import java.util.List;

public class Barcode extends CordovaPlugin {
    public static final String TAG = "Barcode";

    final String showMessageMethod = "showMessage";
    final String initializeCameraMethod = "initializeCamera";
    final String releaseCameraMethod = "releaseCamera";
    final String setViewfinderDimensionMethod = "setViewfinderDimension";
    final String getCameraObjectMethod = "getCameraObject";
    final String startCameraMethod = "startCamera";
    final String stopCameraMethod = "stopCamera";
    final String getBarcodeMethod = "getBarcode";

    public String barcode = "";


    private Camera camera = null;

    /**
     * Constructor.
     */
    public Barcode() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.v(TAG, "Init Barcode");
    }

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Action: " + action);

        if (showMessageMethod.equals(action)) {
            return this.showMessage(args, callbackContext);
        } else if (initializeCameraMethod.equals(action)) {
            return this.initializeCamera(args, callbackContext);
        } else if (releaseCameraMethod.equals(action)) {
            return this.releaseCamera(args, callbackContext);
        } else if (setViewfinderDimensionMethod.equals(action)) {
            return this.setViewfinderDimension(args, callbackContext);
        } else if (getCameraObjectMethod.equals(action)) {
            return this.getCameraObject(args, callbackContext);
        } else if (startCameraMethod.equals(action)) {
            return this.startCamera(args, callbackContext);
        } else if (stopCameraMethod.equals(action)) {
            return this.stopCamera(args, callbackContext);
        } else if (getBarcodeMethod.equals(action)) {
            return this.getBarcode(args, callbackContext);
        }

        return false;
    }

    public boolean showMessage(JSONArray args, CallbackContext callbackContext) throws JSONException {
        final int duration = Toast.LENGTH_SHORT;
        final String message = args.getString(0);

        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(cordova.getActivity().getApplicationContext(), message, duration);
                toast.show();
            }
        });

        return true;
    }

    public boolean initializeCamera(JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Initialize Camera Method on barcode");

        if (this.camera == null) {
            this.camera = new Camera(this.cordova);

            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "Camera initialized"));

            return true;
        }

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera already initialized"));

        return true;

    }

    public boolean releaseCamera(JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Release Camera Method on barcode");

        if (camera != null) {
            this.camera.release();

            this.camera = null;

            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "Camera successfully released"));

            return true;
        }

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera must be initialized first"));

        return true;
    }

    public boolean setViewfinderDimension(JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (this.camera == null) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera must be initialized first"));

            return true;
        }

        this.camera.setDimension(args.getInt(0), args.getInt(1), args.getInt(2), args.getInt(3));

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "Viewfinder dimension successfully set"));

        return true;
    }

    public boolean startCamera(JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (this.camera == null) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera must be initialized first"));

            return true;
        }

        if (this.camera.isCameraStarted) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera already started"));

            return true;
        }

        this.camera.startPreview();

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "Camera successfully started"));

        return true;
    }

    public boolean stopCamera(JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (this.camera == null) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera must be initialized first"));

            return true;
        }

        if (!this.camera.isCameraStarted) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera is not started"));

            return true;
        }

        this.camera.stopPreview();

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "Camera successfully stopped"));

        return true;
    }


    public boolean getCameraObject(JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (this.camera == null) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera must be initialized first"));

            return true;
        }

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "Camera object " + this.camera.toString()));

        return true;
    }

    public boolean getBarcode(JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (this.camera == null) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera must be initialized first"));

            return true;
        }

        if (!this.camera.isCameraStarted) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Camera is not started"));

            return true;
        }

        this.camera.setBarcodeCallback(callbackContext);

//        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, barcode));

        return true;
    }


}
