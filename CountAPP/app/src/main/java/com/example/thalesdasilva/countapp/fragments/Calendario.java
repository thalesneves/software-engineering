package com.example.thalesdasilva.countapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.thalesdasilva.countapp.ActReceita;
import com.example.thalesdasilva.countapp.MainActivity;
import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.util.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class Calendario extends Fragment {

    private CalendarView calendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Calendário");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.frag_calendario, container, false);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getContext(), "Data: " + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabPlus);
        fab.show();
    }

}//fim da classe
