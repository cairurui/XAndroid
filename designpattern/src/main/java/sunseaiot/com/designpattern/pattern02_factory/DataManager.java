package sunseaiot.com.designpattern.pattern02_factory;

/**
 * Created by charry on 2018/7/5.
 */

public interface DataManager {

    void saveData(String key, String value);

    String getData(String key);
}
