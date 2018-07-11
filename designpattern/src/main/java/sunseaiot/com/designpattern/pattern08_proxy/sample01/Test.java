package sunseaiot.com.designpattern.pattern08_proxy.sample01;

/**
 * Created by charry on 2018/7/11.
 */

public class Test {
    public static void main(String[] args) {
        XiaoCai xiaoCai = new XiaoCai();
        BankWorker bankWorker = new BankWorker(xiaoCai);

        bankWorker.applyCard();

        bankWorker.lostCard();
    }
}
