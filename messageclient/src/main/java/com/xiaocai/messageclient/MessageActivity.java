package com.xiaocai.messageclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.charry.xandroid.UserAidl;

import java.lang.reflect.Proxy;

public class MessageActivity extends AppCompatActivity {

    Button btnName;
    Button btnPassword;
    UserAidl userAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServerService();
            }
        });

        btnName = findViewById(R.id.btn_get_name);
        btnPassword = findViewById(R.id.btn_get_password);


        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAidl != null) {
                    try {
                        String userName = userAidl.getUserName();
                        btnName.setText(userName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(MessageActivity.this,"aidl 返回为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAidl != null) {
                    try {
                        String password = userAidl.getUserPassword();
                        btnPassword.setText(password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(MessageActivity.this,"aidl 返回为空",Toast.LENGTH_SHORT).show();

                }
            }
        });

        startActivity(new Intent(this,MessageActivity.class));
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            userAidl = UserAidl.Stub.asInterface(service);
            Toast.makeText(MessageActivity.this, "建立连接", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MessageActivity.this, "断开连接", Toast.LENGTH_SHORT).show();
        }
    };


    private void startServerService() {

        Intent intent = new Intent();
        intent.setAction("com.charry.xandroid.service.MessageService");
        // 在Android 5.0之后google出于安全的角度禁止了隐式声明Intent来启动Service.也禁止使用Intent filter.否则就会抛个异常出来
        intent.setPackage("com.charry.xandroid");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

}
