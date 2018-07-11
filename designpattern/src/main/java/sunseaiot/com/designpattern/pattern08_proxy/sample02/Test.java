package sunseaiot.com.designpattern.pattern08_proxy.sample02;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by charry on 2018/7/11.
 */

public class Test {
    public static void main(String[] args) {
        final XiaoCai xiaoCai = new XiaoCai();
        IBank iBank = (IBank) Proxy.newProxyInstance(
                IBank.class.getClassLoader(),
                new Class[]{IBank.class},
                new BankDynamicProxyHandler(xiaoCai)
        );

        iBank.applyCard();
        iBank.lostCard();
    }
}
