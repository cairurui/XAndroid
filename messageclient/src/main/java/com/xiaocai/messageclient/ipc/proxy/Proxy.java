package com.xiaocai.messageclient.ipc.proxy;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.xiaocai.messageclient.ipc.Book;
import com.xiaocai.messageclient.ipc.server.BookManager;
import com.xiaocai.messageclient.ipc.server.Stub;

import java.util.List;

/**
 * Created by charry on 2018/4/16.
 */

public class Proxy implements BookManager {

    public static final String DESCRIPTOR = "com.xiaocai.messageclient.ipc.server.BookManager";

    IBinder mRemote;


    public Proxy(IBinder binder) {
        this.mRemote = binder;
    }

    public String getInterfaceDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        List<Book> result = null;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            mRemote.transact(Stub.TRANSAVTION_getBooks, data, replay, 0);
            replay.readException();
            result = replay.createTypedArrayList(Book.CREATOR);
        } finally {
            data.recycle();
            replay.recycle();
        }
        return result;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (book != null) {
                data.writeInt(1);
                book.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }

            mRemote.transact(Stub.TRANSAVTION_addBook, data, replay, 0);
            replay.readException();
        } finally {
            data.recycle();
            replay.recycle();
        }
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
