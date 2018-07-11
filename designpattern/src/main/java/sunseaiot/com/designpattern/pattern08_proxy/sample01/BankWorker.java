package sunseaiot.com.designpattern.pattern08_proxy.sample01;

/**
 * Created by charry on 2018/7/11.
 */

public class BankWorker implements IBank {

    private IBank mRealPerson;

    public BankWorker(IBank real) {
        this.mRealPerson = real;

    }

    @Override
    public void applyCard() {
        System.out.println("开始受理");
        mRealPerson.applyCard();
        System.out.println("受理结束");
    }

    @Override
    public void lostCard() {
        System.out.println("开始受理");
        mRealPerson.lostCard();
        System.out.println("受理结束");
    }
}
