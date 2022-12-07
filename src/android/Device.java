package com.branddocs.nfc.device;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.branddocs.nfc.NewActivity;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Device extends CordovaPlugin {
    public static final String TAG = "Device";
    public static String platform; // Device OS
    public static String uuid; // Device UUID

    private DeviceInfo deviceInfo;

    /**
     * Constructor.
     */
    public Device() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(
            CordovaInterface cordova
            , CordovaWebView webView) {

        super.initialize(
                cordova
                , webView);

        this.deviceInfo
                = new DeviceInfo(
                        cordova);

        Device.uuid
                = this.deviceInfo
                    .getUuid();
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action          The action to execute.
     * @param args            JSONArry of arguments for the plugin.
     * @param callbackContext The callback id used when calling back into
     *                        JavaScript.
     * @return True if the action was valid, false if not.
     */
    public boolean execute(
            String action
            , JSONArray args
            , CallbackContext callbackContext) throws JSONException {

        Log.d( "Device.execute", action );

        if ("getDeviceInfo".equals(action)) {
            callbackContext.success(
                    getInfoAsJsonObject() );
        }
        else if ("getVideoTest".equals(action)) {
            callbackContext.success(
                    getVideIdTestDataAsJsonObject() );
        }
        else if ("createNewActivity".equals(action)) {
            Context context
                    = this.cordova
                        .getActivity()
                        .getApplicationContext();

            this.openNewActivity(
                    context);
        }else {
            return false;
        }
        return true;
    }

    private void openNewActivity(Context context) {

        Class newActivityClass
            = NewActivity.class;

        Intent intent
                = new Intent(
                        context
                        , newActivityClass);

        this.cordova
                .getActivity()
                .startActivity(
                        intent);
    }

    @NonNull
    private JSONObject getVideIdTestDataAsJsonObject() throws JSONException {
        JSONObject resultAsJson = new JSONObject();

        resultAsJson.put(
                "testData"
                , "This is coming from the native Android code");

        return resultAsJson;
    }

    @NonNull
    private JSONObject getInfoAsJsonObject() throws JSONException {
        JSONObject resultAsJson = new JSONObject();

        resultAsJson.put("uuid", Device.uuid);
        resultAsJson.put("version", this.deviceInfo.getOSVersion());
        resultAsJson.put("platform", this.deviceInfo.getPlatform());
        resultAsJson.put("model", this.deviceInfo.getModel());
        resultAsJson.put("manufacturer", this.deviceInfo.getManufacturer());
        resultAsJson.put("isVirtual", this.deviceInfo.isVirtual());
        resultAsJson.put("serial", this.deviceInfo.getSerialNumber());
        resultAsJson.put("sdkVersion", this.deviceInfo.getSDKVersion());

        return resultAsJson;
    }
}
