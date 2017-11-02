package com.example.thalesdasilva.countapp;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by ThalesdaSilva on 27/10/2017.
 */

public class MyValueFormatter implements IValueFormatter {

    private DecimalFormat decimalFormat;

    public MyValueFormatter() {
        decimalFormat = new DecimalFormat("######.0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return "R$ " + decimalFormat.format(value);
    }

}//fim da classe
