package com.example.gamesnake.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class FonctionWIFI {
    public boolean enableWifi;
    public String ipAddress;
    private Context context;
    private WifiInfo wifiInfo;
    public FonctionWIFI(Context context){
        this.context = context;
        checkWifiEnable();
        getIpAddress();
    }

    public boolean checkWifiEnable(){
        NetworkInfo wifiStatus =((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((wifiStatus != null) && (wifiStatus.isAvailable())){
            enableWifi = true;
        }else {
            enableWifi = false;
        }

        return enableWifi;
    }

    public String getIpAddress(){
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifiInfo = wifiManager.getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                ipAddress =( (ip >> 0)  & 0xFF ) + "." +
                            ( (ip >> 8)  & 0xFF ) + "." +
                            ( (ip >> 16) & 0xFF ) + "." +
                            ( (ip >> 24) & 0xFF ) ;




            }catch (Exception e ){
                e.printStackTrace();
                ipAddress ="0";
            }





            return ipAddress;
    }

}
