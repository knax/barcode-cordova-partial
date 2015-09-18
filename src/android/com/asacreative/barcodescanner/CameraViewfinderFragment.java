package com.asacreative.barcodescanner;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CameraViewfinderFragment extends Fragment {
    private static final String TAG = CameraViewfinderFragment.class.getSimpleName();
    private String appResourcesPackage;
    private View view;

    private int x;
    private int y;
    private int width;
    private int height;
    private FrameLayout frameContainerLayout;
    private CompoundBarcodeView barcodeView;

    private CallbackContext pluginContext;

    public void setDimension(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                barcodeView.setStatusText(result.getText());
                pluginContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result.getText()));
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    public void setBarcodeCallback(CallbackContext barcodeCallback) {
        this.pluginContext = barcodeCallback;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "On create view");

        appResourcesPackage = this.getActivity().getPackageName();

        // Inflate the layout for this fragment
        view = inflater.inflate(this.getResources().getIdentifier("activity_camera", "layout", appResourcesPackage), container, false);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.setMargins(x, y, 0, 0);

        frameContainerLayout = (FrameLayout) view.findViewById(getResources().getIdentifier("frame_container", "id", appResourcesPackage));
        frameContainerLayout.setLayoutParams(layoutParams);


        barcodeView = (CompoundBarcodeView) view.findViewById(getResources().getIdentifier("barcode_scanner", "id", appResourcesPackage));
        barcodeView.decodeContinuous(callback);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CameraViewfinderFragment", "on resume");
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        barcodeView.pause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}