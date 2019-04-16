package com.example.projectweekly.BroadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.projectweekly.Interfaces.NetworkStateChangeListener;

public class NetworkStateReciever extends BroadcastReceiver {

    NetworkStateChangeListener onStateChange;

    public void setOnNetworkStateChanged(NetworkStateChangeListener onNetworkStateChanged) {
        this.onStateChange = onNetworkStateChanged;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean notConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if(notConnected){
                onStateChange.isNetworkConnected(false);

            }
            else{
                onStateChange.isNetworkConnected(true);

            }
        }
    }
}
