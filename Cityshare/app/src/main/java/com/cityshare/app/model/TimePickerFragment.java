package com.cityshare.app.model;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by vagne_000 on 08/05/2017.
 */

public class TimePickerFragment extends DialogFragment {
    TimePickerDialog.OnTimeSetListener timeSet;

    public TimePickerFragment(TimePickerDialog.OnTimeSetListener listener) {
        this.timeSet = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hora = c.get( Calendar.HOUR );
        int minuto = c.get( Calendar.MINUTE );

        return new TimePickerDialog(getActivity(), timeSet, hora, minuto, true);
    }
}
