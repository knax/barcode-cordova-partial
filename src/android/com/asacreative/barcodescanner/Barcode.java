package com.asacreative.barcodescanner;

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

public class Barcode extends CordovaPlugin {
    public static final String TAG = "Barcode";

    final String showMessageMethod = "showMessage";
    final String initializeCameraMethod = "initializeCamera";
    final String releaseCameraMethod = "releaseCamera";
    final String setViewfinderDimensionMethod = "setViewfinderDimension";


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

        this.camera = new Camera();

        return true;
    }

    public boolean releaseCamera(JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Release Camera Method on barcode");

        this.camera.release();

        this.camera = null;

        return true;
    }

    public boolean setViewfinderDimension(JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.camera.setDimension(args.getInt(0),args.getInt(1),args.getInt(2),args.getInt(3));

        return true;
    }


}
