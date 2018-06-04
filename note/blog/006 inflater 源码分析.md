
LayoutInflater 的一般获取是 from，直接看下源码

``` java
   /**
     * Obtains the LayoutInflater from the given context.
     */
    public static LayoutInflater from(Context context) {
        LayoutInflater LayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (LayoutInflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }
        return LayoutInflater;
    }
```


调用了 Comtext 的 getSystemService,这个方法得到的是单例的后面会分析到，这个方法很重要，可以获取到很多系统对象

```
// 调用了 Comtext 的 getSystemService
public abstract @Nullable Object getSystemService(@ServiceName @NonNull String name);

```

虽然已经知道 Comtext 的实现是 ComtextImpl，怎么验证就是 ComtextImpl 呢？Activity 的启动流程！
Activity 是由 ActivityThread 其中的 main 方法启动的

``` java

    public static void main(String[] args) {
        ...
        Looper.prepareMainLooper();

        ActivityThread thread = new ActivityThread();
        thread.attach(false);

        if (sMainThreadHandler == null) {
            sMainThreadHandler = thread.getHandler();
        }
        ...
        Looper.loop();

        throw new RuntimeException("Main thread loop unexpectedly exited");
    }

```

在创建来 ActivityThread 对象之后调用 thread.attach(false)，里面的参数代表是否为系统应用，这里明显不是系统应用，继续看这个方法。
``` java

    private void attach(boolean system) {

        if (!system) {

            ViewRootImpl.addFirstDrawHandler(new Runnable() {
                @Override
                public void run() {
                    ensureJitEnabled();
                }
            });
            android.ddm.DdmHandleAppName.setAppName("<pre-initialized>",
                                                    UserHandle.myUserId());
            RuntimeInit.setApplicationObject(mAppThread.asBinder());
            final IActivityManager mgr = ActivityManager.getService();
            try {
                mgr.attachApplication(mAppThread); // 关联 mAppThread
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }

        } else {

        }
        ...
    }

```

这里涉及到来 Binder 的东西了，但最终 Binder 会回调给 Handler
```
    // H 是 Activity 的内部类
    private class H extends Handler {
        public void handleMessage(Message msg) {
            if (DEBUG_MESSAGES) Slog.v(TAG, ">>> handling: " + codeToString(msg.what));
            switch (msg.what) {
                case LAUNCH_ACTIVITY: {
                    Trace.traceBegin(Trace.TRACE_TAG_ACTIVITY_MANAGER, "activityStart");
                    final ActivityClientRecord r = (ActivityClientRecord) msg.obj;

                    r.packageInfo = getPackageInfoNoCheck(
                            r.activityInfo.applicationInfo, r.compatInfo);
                    handleLaunchActivity(r, null, "LAUNCH_ACTIVITY");
                    Trace.traceEnd(Trace.TRACE_TAG_ACTIVITY_MANAGER);
                } break;
```

在 handler 中调用了 handleLaunchActivity 方法，这个就是启动 Activity 的方法了

``` java

    private void handleLaunchActivity(ActivityClientRecord r, Intent customIntent, String reason) {
        WindowManagerGlobal.initialize();
        // 创建 activity
        Activity a = performLaunchActivity(r, customIntent);

        if (a != null) {
            r.createdConfig = new Configuration(mConfiguration);
            reportSizeConfigurations(r);
            Bundle oldState = r.state;
            // 接着调用 handleResumeActivity
            handleResumeActivity(r.token, false, r.isForward,
                    !r.activity.mFinished && !r.startsNotResumed, r.lastProcessedSeq, reason);

        } else {
            ...
        }
    }
```


```

    private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
        // 创建 context
        ContextImpl appContext = createBaseContextForActivity(r);
        Activity activity = null;
        try {
            // 创建 activity
            java.lang.ClassLoader cl = appContext.getClassLoader();
            activity = mInstrumentation.newActivity(
                    cl, component.getClassName(), r.intent);
        } catch (Exception e) {
            ...
        }

        try {
            // 获取 application
            Application app = r.packageInfo.makeApplication(false, mInstrumentation);

            if (activity != null) {
                CharSequence title = r.activityInfo.loadLabel(appContext.getPackageManager());
                Configuration config = new Configuration(mCompatConfiguration);
                appContext.setOuterContext(activity);
                activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window, r.configCallback);


                activity.mCalled = false;
                // 调用 callActivityOnCreate 会调用 activity的 oncreate 方法
                if (r.isPersistable()) {
                    mInstrumentation.callActivityOnCreate(activity, r.state, r.persistentState);
                } else {
                    mInstrumentation.callActivityOnCreate(activity, r.state);
                }
                ...

        return activity;
    }
```
上面调用
- createBaseContextForActivity 为 activity 创建 context
- 创建 activity 实例对象
- 调用 activity 的 attach
- 调用 mInstrumentation.callActivityOnCreate 走到 activity 的 oncreate

接着看到 createBaseContextForActivity

```
    private ContextImpl createBaseContextForActivity(ActivityClientRecord r) {
        ...
        ContextImpl appContext = ContextImpl.createActivityContext(
                this, r.packageInfo, r.activityInfo, r.token, displayId, r.overrideConfig);
       ...
        return appContext;
    }

```

这里证明了 context 的具体实现是 ContextImpl

继续回到最初的问题，看 context 的 getSystemService 具体实现
```
    @Override
    public Object getSystemService(String name) {
        return SystemServiceRegistry.getSystemService(this, name);
    }
```
这里看到的是 api 27 的版本，跟之前版本有很大的不同，api 27 版本单独抽出了 SystemServiceRegistry 来做管理四通服务
```
    private static final HashMap<String, ServiceFetcher<?>> SYSTEM_SERVICE_FETCHERS =
            new HashMap<String, ServiceFetcher<?>>();
    public static Object getSystemService(ContextImpl ctx, String name) {
        ServiceFetcher<?> fetcher = SYSTEM_SERVICE_FETCHERS.get(name);
        return fetcher != null ? fetcher.getService(ctx) : null;
    }
```
SYSTEM_SERVICE_FETCHERS 是 HashMap 用于做缓存，所以得到的将是个单例对象。
```

    static {
        // 在静态代码块中将会注册很多的系统服务
        ...

        registerService(Context.LAYOUT_INFLATER_SERVICE, LayoutInflater.class,
                new CachedServiceFetcher<LayoutInflater>() {
            @Override
            public LayoutInflater createService(ContextImpl ctx) {
                return new PhoneLayoutInflater(ctx.getOuterContext());
            }});

        ...
    }
```
到这里看到 LayoutInflater 的具体实现是 PhoneLayoutInflater。

小结
- LayoutInflater 是由 context.getSystemService 获取的
- ActivityThread 中开启的启动流程 main
- Activity 是由反射创建的
- Context 的具体实现是 ContextImpl
- Context 的 getSystemService 将返回 单例 的系统服务对象
- LayoutInflater 的具体实现是 PhoneLayoutInflater



### LayoutInflater 的解析视图的流程




