package com.dji.sdk.sample.internal.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.dji.sdk.sample.internal.controller.DJISampleApplication;

import timber.log.Timber;

/**
 * Created by DJI on 2/28/17.
 */

public class ToastUtils {
    private static final int MESSAGE_UPDATE = 1;
    private static final int MESSAGE_TOAST = 2;
    private static Handler mUIHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //Get the message string
            switch ((msg.what)) {
                case MESSAGE_UPDATE:
                    showMessage((Pair<TextView, String>) msg.obj);
                    break;
                case MESSAGE_TOAST:
                    showToast((String) msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private static void showMessage(Pair<TextView, String> msg) {
        if (msg != null) {
            if (msg.first == null) {
                Toast.makeText(DJISampleApplication.Companion.getInstance(), "tv is null", Toast.LENGTH_SHORT).show();
            } else {
                msg.first.setText(msg.second);
            }
        }
    }

    public static void showToast(String msg) {
        Toast.makeText(DJISampleApplication.Companion.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void setResultToToast(final String message) {
        Timber.d(message);
        Message msg = new Message();
        msg.what = MESSAGE_TOAST;
        msg.obj = message;
        mUIHandler.sendMessage(msg);
    }

    public static void setResultToText(final TextView tv, final String message) {
        Timber.d(message);
        Message msg = new Message();
        msg.what = MESSAGE_UPDATE;
        msg.obj = new Pair<>(tv, message);
        mUIHandler.sendMessage(msg);
    }
}
