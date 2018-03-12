package com.zpf.oillogistics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/10/9.
 */

public class DetailsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        ReceiveChangeListener. onChange();
    }

    ReceiveChangeListener ReceiveChangeListener;

    public void setReceiveChangeListener(DetailsReceiver.ReceiveChangeListener receiveChangeListener) {
        ReceiveChangeListener = receiveChangeListener;
    }

    public interface ReceiveChangeListener{
        void onChange();
    }
}
