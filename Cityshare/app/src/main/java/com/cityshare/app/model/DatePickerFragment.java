package com.cityshare.app.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by vagne_000 on 08/05/2017.
 */

public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener listener;

    public DatePickerFragment(DatePickerDialog.OnDateSetListener dateSet) {
        this.listener = dateSet;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int dia = c.get( Calendar.DAY_OF_MONTH );
        int mes = c.get( Calendar.MONTH );
        int ano = c.get( Calendar.YEAR );

        return new DatePickerDialog(getActivity(), listener, ano, mes, dia);
    }
}
