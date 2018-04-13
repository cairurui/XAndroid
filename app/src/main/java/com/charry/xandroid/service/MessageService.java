package com.charry.xandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.charry.xandroid.IMessageManager;
import com.charry.xandroid.IOnMessageArrivedListener;
import com.charry.xandroid.utils.Xlog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageService extends Service {
    CopyOnWriteArrayList<Message> mMessageLists;
    AtomicBoolean mIsServiceRunning ;

    private RemoteCallbackList<IOnMessageArrivedListener> mListenerList = new RemoteCallbackList<>();

    {

        mMessageLists = new CopyOnWriteArrayList<>();
        mMessageLists.add(new Message(1111, "first message"));
        mMessageLists.add(new Message(2222, "second message"));
        mMessageLists.add(new Message(3333, "three message"));
    }


    public MessageService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Xlog.d("MessageService onCreate");
        mIsServiceRunning = new AtomicBoolean(true) ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceRunning.set(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder.asBinder();
    }


    IMessageManager.Stub mBinder = new IMessageManager.Stub() {

        @Override
        public List<Message> getMessages() throws RemoteException {
            return mMessageLists;
        }

        @Override
        public void addMessage(Message message) throws RemoteException {
            mMessageLists.add(message);
        }

        @Override
        public void registerMessageListener(IOnMessageArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
            int count = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Xlog.d("registerMessageListener IOnMessageArrivedListener count:" + count);

        }

        @Override
        public void unregisterMessageListener(IOnMessageArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            int count = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Xlog.d("unregisterMessageListener IOnMessageArrivedListener count:" + count);

        }
    };



}
