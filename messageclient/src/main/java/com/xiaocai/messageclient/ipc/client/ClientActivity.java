package com.xiaocai.messageclient.ipc.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaocai.messageclient.R;
import com.xiaocai.messageclient.ipc.Book;
import com.xiaocai.messageclient.ipc.server.BookManager;
import com.xiaocai.messageclient.ipc.server.RemoteService;
import com.xiaocai.messageclient.ipc.server.Stub;

import java.util.List;
import java.util.Random;

public class ClientActivity extends AppCompatActivity {
    BookManager mBookManager;
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        tv_content = findViewById(R.id.tv_content);
    }

    public void startBookManager(View view) {

        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = Stub.asInterface(service);
            Toast.makeText(ClientActivity.this,"连接上了",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
            Toast.makeText(ClientActivity.this,"断开连接",Toast.LENGTH_SHORT).show();
        }
    };


    public void addBook(View view) {
        Random random = new Random();
        int price = random.nextInt(100);
        String name = "name of price " + price;
        Book book = new Book(price, name);
        try {
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void getBooks(View view) {
        synchronized (this) {
            try {
                List<Book> books = mBookManager.getBooks();
                if (books == null) return;

                StringBuilder sb = new StringBuilder();
                for (Book book : books) {
                    if (book != null)
                        sb.append(book.toString() + "\r\n");
                }

                showContent(sb.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    private void showContent(String content) {
        tv_content.setText(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBookManager != null) {
            unbindService(conn);
        }
    }
}
