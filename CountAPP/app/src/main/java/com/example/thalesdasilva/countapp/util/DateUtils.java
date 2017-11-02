package com.example.thalesdasilva.countapp.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ThalesdaSilva on 02/08/2017.
 */

public class DateUtils {

    public static String dateTostring(int year, int month, int dayOfMonth) {

        //vai auxiliar e já criar a instância do objeto da classe Calendar
        //e já vai retornar a data do sistema por padrão
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        Date date = calendar.getTime();
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(date);

        return dt;

    }

    public static String dateTostring(Date date) {

        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        String dt = format.format(date);

        return dt;

    }

    public static Date getDate(int year, int month, int dayOfMonth) {

        //vai auxiliar e já criar a instância do objeto da classe Calendar
        //e já vai retornar a data do sistema por padrão
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        Date date = calendar.getTime();

        return date;

    }

}//fim da classe
