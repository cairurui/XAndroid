// IOnMessageArrivedListener.aidl
package com.charry.xandroid;

// Declare any non-default types here with import statements
import com.charry.xandroid.service.Message;

interface IOnMessageArrivedListener {
    void onMessageArrived(in Message message);
}
