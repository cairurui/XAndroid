# 手动写 AIDL 过程

### 1. bean 文件
aidl 中可传递的类型有基本数据类型，string，list，map 等，但不是所有都可以，对于一些自定义的 bean 需要实现 Parcelable 接口

```

public class Book implements Parcelable {

    private int price;
    private String name;

    public Book(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(price);
        dest.writeString(name);
    }

    // 需要注意顺序，要与 writeToParcel 一致
    protected Book(Parcel in) {
        price = in.readInt();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
```


### 2. stub 类
这个类运行时已经在 server 端了，是 抽象类，负责调度功能接口。
```

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
```


### 3. 代理类 Proxy
这个类运行时还是在 Client 端，负责跟 Binder 进行对接，掉起 Binder。

```
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

```

### 4. 服务端具体实现 RemoteService
在清单文件中需要 process，让其在另一个进程中运行。在 onBind 中返回 Stub 对象。
```

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
                return new ArrayList<Book>();
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
```

### 5. 客户端 ClientActivity
正常的 bind 方式使用即可
```

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

```