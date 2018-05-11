package com.example.congcanh.elearningproject.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.example.congcanh.elearningproject.model.MyConstants;

/**
 * Created by CongCanh on 4/18/2018.
 */

@SuppressLint("ValidFragment")
public class PickupTimeDialogFragment extends DialogFragment {
    private int timeHour;
    private int timeMinute;
    private Handler handler;

    @SuppressLint("ValidFragment")
    public PickupTimeDialogFragment(Handler handler){
        this.handler = handler;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        timeHour = bundle.getInt(MyConstants.HOUR);
        timeMinute = bundle.getInt(MyConstants.MINUTE);
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeHour = hourOfDay;
                timeMinute = minute;
                Bundle b = new Bundle();
                b.putInt(MyConstants.HOUR, timeHour);
                b.putInt(MyConstants.MINUTE, timeMinute);
                Message msg = new Message();
                msg.setData(b);
                handler.sendMessage(msg);
            }
        };
        return new TimePickerDialog(getActivity(), listener, timeHour, timeMinute, false);
    }
}
