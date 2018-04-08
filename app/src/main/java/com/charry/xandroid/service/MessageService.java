package com.charry.xandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.charry.xandroid.UserAidl;

public class MessageService extends Service {

    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    UserAidl.Stub mBinder = new UserAidl.Stub() {
        @Override
        public String getUserName() {
            return "xiaocai";
        }

        @Override
        public String getUserPassword() {
            return "123456";
        }
    };

}
