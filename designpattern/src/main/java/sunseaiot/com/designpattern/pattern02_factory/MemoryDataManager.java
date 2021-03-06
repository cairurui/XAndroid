package sunseaiot.com.designpattern.pattern02_factory;

import android.util.Log;

/**
 * Created by charry on 2018/7/5.
 */

public class MemoryDataManager implements DataManager {

    private String TAG = "DiskDataManager";

    @Override
    public void saveData(String key, String value) {
        Log.d(TAG, "saveData() called with: key = [" + key + "], value = [" + value + "]");
    }

    @Override
    public String getData(String key) {
        Log.d(TAG, "getData() called with: key = [" + key + "]");
        return "getDataWith" + key;
    }

}
