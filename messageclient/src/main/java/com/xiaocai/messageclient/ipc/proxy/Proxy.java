package com.xiaocai.messageclient.ipc.proxy;

import android.os.IBinder;
import android.os.RemoteException;

import com.xiaocai.messageclient.ipc.Book;
import com.xiaocai.messageclient.ipc.server.BookManager;

import java.util.List;

/**
 * Created by charry on 2018/4/16.
 */

public class Proxy implements BookManager {


    public Proxy(IBinder binder) {

    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        return null;
    }

    @Override
    public void addBook(Book book) throws RemoteException {

    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
