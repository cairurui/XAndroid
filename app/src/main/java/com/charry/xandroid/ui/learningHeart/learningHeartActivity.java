package com.charry.xandroid.ui.learningHeart;

import android.os.Bundle;
import android.view.View;

import com.charry.xandroid.R;
import com.charry.xandroid.base.BaseActivity;
import com.charry.xandroid.base.BasePresenter;
import com.charry.xandroid.utils.UIUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.OnClick;

public class learningHeartActivity extends BaseActivity {


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_learning_heart;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    String serv = "192.168.199.100";
    int port = 1234;


    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());
    private static final int BUFFER_SIZE = 1024;

    @OnClick(R.id.btn)
    public void test(View v) {

        UIUtil.toash("开始连接");
        v.setEnabled(false);


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Socket s = new Socket(serv, port);
                    PrintWriter out = new PrintWriter(s.getOutputStream());
                    InputStream in = s.getInputStream();

                    byte[] recData = null;

                    while (true) {
                        date.setTime(System.currentTimeMillis());
                        String msg = "cline " + format.format(date);
                        out.println(msg);
                        out.flush();
                        recData = new byte[BUFFER_SIZE];
                        int r = in.read(recData);
                        if(r>-1) {
                            String data = new String(recData);
                            if(data.trim().equals("over")) {
                                s.close();
                            }
                            System.out.println("收到服务端发送的来数据："+data);
                        }

                        //这里改成1秒了  这样就是心跳包了
                        Thread.sleep(1000);


//                        Thread.sleep(3000);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
