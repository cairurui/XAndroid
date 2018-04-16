package com.xiaocai.messageclient.ipc.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.xiaocai.messageclient.ipc.Book;
import com.xiaocai.messageclient.ipc.proxy.Proxy;

import java.util.List;

/**
 * Created by charry on 2018/4/16.
 */

public abstract class Stub extends Binder implements BookManager {

    public static final String DESCRIPTOR = "com.xiaocai.messageclient.ipc.server.BookManager";
    public static final int TRANSAVTION_getBooks = IBinder.FIRST_CALL_TRANSACTION;
    public static final int TRANSAVTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public Stub() {
        attachInterface(this, DESCRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     * 给 Client 方调用的，用来转换为需要的 Server 对象（在同一进程的情况）或者是其代理对象（跨进程时）
     *
     * 当 Client 端在创建和服务端的连接， 调用 bindService 时需要创建一个 ServiceConnection 对象作为入参。
     * 在 ServiceConnection 的回调方法 onServiceConnected 中 会通过这个 asInterface(IBinder binder) 拿到 BookManager 对象，
     * 这个 IBinder 类型的入参 binder 是驱动传给我们的，正如你在代码中看到的一样，
     * 方法中会去调用 binder.queryLocalInterface() 去查找 Binder 本地对象，
     * 如果找到了就说明 Client 和 Server 在同一进程，那么这个 binder 本身就是 Binder 本地对象，可以直接使用。
     * 否则说明是 binder 是个远程对象，也就是 BinderProxy。因此需要我们创建一个代理对象 Proxy，
     * 通过这个代理对象来是实现远程访问。
     */
    public static BookManager asInterface(IBinder binder) {
        if (binder == null) {
            return null;
        }
        IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof BookManager) {
            return (BookManager) iInterface;
        }

        return new Proxy(binder);

    }


    /**
     * 以 addBook 为例：
     * Client 端调用 addBook --> binder 代理调用 addBook --> 一系列中间层调用后，找到了 Server 端对应的 Binder 对象
     * --> Binder 对象的 transact --> 来到这个 Stub 来的 onTransact，判断了是 addBook 方法 --> 调用具体实现 Server 类的 addBook
     */
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSAVTION_getBooks:
                data.enforceInterface(DESCRIPTOR);
                List<Book> books = this.getBooks();
                reply.writeNoException();
                reply.writeTypedList(books);
                return true;
            case TRANSAVTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book book = null;
                if (data.readInt() != 0) {
                    book = Book.CREATOR.createFromParcel(data);
                }
                this.addBook(book);
                reply.writeNoException();
                return true;
        }

        return super.onTransact(code, data, reply, flags);
    }
}
