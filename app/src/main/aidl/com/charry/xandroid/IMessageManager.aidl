// IMessageManager.aidl
package com.charry.xandroid;

// Declare any non-default types here with import statements
import com.charry.xandroid.service.Message;
import com.charry.xandroid.IOnMessageArrivedListener;

interface IMessageManager {

    List<Message> getMessages(); // 获取消息

    void addMessage(in Message message);

    void registerMessageListener(IOnMessageArrivedListener listener);

    void unregisterMessageListener(IOnMessageArrivedListener listener);

}
