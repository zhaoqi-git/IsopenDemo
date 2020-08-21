package com.alpine.team1.isopendemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView blue,wifi;
    private WifiManager wifiManager;
    private String TAG = "Wifi";
    private Timer timer = new Timer(true);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blue = findViewById(R.id.blue);
        wifi = findViewById(R.id.wifi);
        timer.schedule(task,0,2000);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2){
                setState();
            }
        }
    };
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    };
    private void setState(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Boolean isopen = bluetoothAdapter.isEnabled();
        if (isopen){
            blue.setImageResource(R.drawable.blueteeth);
        }else {
            blue.setImageResource(R.color.colorAccent);
        }
        wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            int wifiState = wifiManager.getWifiState();
            Log.d(TAG, String.valueOf(wifiState));
            switch (wifiState) {
                case 1://未打开
                    wifi.setImageResource(R.color.colorAccent);
                    break;
                case 3://已打开
                    wifi.setImageResource(R.drawable.wifi);
                    break;
                default:
                    break;
            }
        }
    }
}