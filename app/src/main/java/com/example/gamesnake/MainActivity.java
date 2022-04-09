package com.example.gamesnake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.example.gamesnake.network.FonctionWIFI;
import com.example.gamesnake.network.SocketBluetooth;
import com.example.gamesnake.network.SocketWifi;

public class MainActivity extends Activity {

    public static SocketWifi sw;
    public static Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this);
        setContentView(game);
        FonctionWIFI fonctionWIFI = new FonctionWIFI(this);
        System.out.println("WIFI IS :" + fonctionWIFI.enableWifi+" IP @: "+fonctionWIFI.ipAddress);
        if (fonctionWIFI.enableWifi) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    sw = new SocketWifi("82.165.59.229", 2333, game);
                    sw.startReceivingInformation(sw);
                    while (true) {
                        sw.sendInformation();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            System.out.println("Error: Network Sleep");
                        }

                    }

                }
            });
            thread.start();
        }


        if( BluetoothAdapter.getDefaultAdapter().isEnabled()){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SocketBluetooth sb = new SocketBluetooth(game);
                    sb.startReceivingInformation(sb);

                while(true){
                    sb.sendInformation();
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e ){
                        System.out.println("Error: BlueTooth Sleep");
                    }
                }
                }
            });
            thread.start();

        }

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideBottomNav(this);
        }
    }
    public static void hideBottomNav(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }








    }