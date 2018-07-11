package sunseaiot.com.designpattern.pattern08_proxy.sample02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by charry on 2018/7/11.
 */

public class BankDynamicProxyHandler implements InvocationHandler {

    private IBank mRealBank;

    public BankDynamicProxyHandler(IBank real) {
        this.mRealBank = real;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理-开始受理");
        Object invoke = method.invoke(mRealBank, args);
        System.out.println("动态代理-结束代理");
        return invoke;
    }
}
