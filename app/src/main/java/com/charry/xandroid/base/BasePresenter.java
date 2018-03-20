package com.charry.xandroid.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;

/**
 * Created by xiaocai on 2018/1/22.
 */

public class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {

    // 一个是原始的 View ，一个是代理的 View
    private WeakReference<V> mView = null;
    private V mProxyView = null;
    protected M model = null;

    @Override
    public void attachView(V view) {
        this.mView = new WeakReference<>(view);
        // 动态代理
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (mView != null && mView.get() != null) {
                            return method.invoke(mView.get(), args);
                        }
                        return null;
                    }
                });

        // 注入 Model，怎么注入，获取泛型的类型，也就是 M 的 class，利用反射new 一个对象
        try {
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<M> modelClazz = (Class<M>) (parameterizedType.getActualTypeArguments()[1]);
            model = modelClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public V getView() {
        return mProxyView;
    }
}
