package com.xiaocai.messageclient.ipc.server;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.xiaocai.messageclient.ipc.Book;

import java.util.List;

/**
 * Created by charry on 2018/4/16.
 */

public interface BookManager extends IInterface {

    List<Book> getBooks() throws RemoteException;

    void addBook(Book book) throws RemoteException;

}
