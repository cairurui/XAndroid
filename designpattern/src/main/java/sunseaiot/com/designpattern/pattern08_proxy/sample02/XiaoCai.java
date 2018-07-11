package sunseaiot.com.designpattern.pattern08_proxy.sample02;


/**
 * Created by charry on 2018/7/11.
 */

public class XiaoCai implements IBank {

    @Override
    public void applyCard() {
        System.out.println("小菜--申请办卡");
    }

    @Override
    public void lostCard() {
        System.out.println("小菜--申请挂失");
    }
}
