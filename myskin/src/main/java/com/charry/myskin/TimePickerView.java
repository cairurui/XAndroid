package com.charry.myskin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by charry on 2018/6/14.
 */

public class TimePickerView extends FrameLayout {

    private NumberPicker picker_hour;
    private NumberPicker picker_minute;


    public TimePickerView(@NonNull Context context) {
        this(context, null);
    }

    public TimePickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_time_picker, this);
        picker_hour = findViewById(R.id.picker_hour);
        picker_minute = findViewById(R.id.picker_minute);
    }

    public int getHour() {
        return picker_hour.getValue();
    }

    public int getMinute() {
        return picker_minute.getValue();
    }

    public void setHour(int hour) {
        picker_hour.setValue(hour);
    }

    public void setMinute(int minute) {
        picker_minute.setValue(minute);
    }


}
