package com.xiaocai.messageclient.ipc.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.xiaocai.messageclient.ipc.Book;

import java.util.ArrayList;
import java.util.List;

public class RemoteService extends Service {

    private List<Book> mBooks = new ArrayList<>();

    {
        mBooks.add(new Book(100, "深入理解 java"));
        mBooks.add(new Book(200, "深入理解 android"));
        mBooks.add(new Book(300, "深入理解 JavaScript"));
    }

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private final Stub stub = new Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                if (mBooks != null)
                    return mBooks;
                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (book == null) return;

                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                mBooks.add(book);
            }
        }
    };
}
