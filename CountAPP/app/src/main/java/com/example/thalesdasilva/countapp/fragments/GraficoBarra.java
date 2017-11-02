package com.example.thalesdasilva.countapp.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.example.thalesdasilva.countapp.ActLogin;
import com.example.thalesdasilva.countapp.MainActivity;
import com.example.thalesdasilva.countapp.MyValueFormatter;
import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioUsuario;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraficoBarra extends Fragment {

    private BarChart barChart;

    private DataBase database;
    private SQLiteDatabase conn;
    public RepositorioUsuario repositorioUsuario;

    Long IDPerfil = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_gerar_grafico_barra, container, false);

        super.onViewCreated(view, savedInstanceState);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBarChart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Gráficos");
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabPlus);
        fab.show();
        setupBarChart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_grafico_barra, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().finish();
        } else if (id == R.id.menuGraficoCircular) {

            Grafico grafico = new Grafico();
            Bundle bundle = getActivity().getIntent().getExtras();
            grafico.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, grafico, grafico.getTag()).commit();

            manager.addOnBackStackChangedListener(null);

        }

        return super.onOptionsItemSelected(item);
    }

    private void setupBarChart() {

        barChart = (BarChart) getView().findViewById(R.id.bargraph);

        database = new DataBase(getContext());
        conn = database.getWritableDatabase();

        repositorioUsuario = new RepositorioUsuario(conn);

        ArrayList<String> dados = database.buscarPerfil(ActLogin.nome);

        IDPerfil = Long.parseLong(dados.get(0));

        Float dadosReceitas = database.somarReceita(IDPerfil);
        Float dadosDespesas = database.somarDespesa(IDPerfil);

        Float saldoReceitas[] = {dadosReceitas};
        Float saldoDespesas[] = {dadosDespesas};

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();

        for (int i = 0; i < saldoReceitas.length; i++) {
            barEntries.add(new BarEntry(1, saldoReceitas[i]));
        }

        for (int l = 0; l < saldoDespesas.length; l++) {
            barEntries1.add(new BarEntry(2, saldoDespesas[l]));
        }

        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        BarDataSet barDataSet = new BarDataSet(barEntries, "Receitas");
        barDataSet.setColor(getResources().getColor(R.color.receitasForte));

        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Despesas");
        barDataSet1.setColor(getResources().getColor(R.color.despesasForte));

        //Tira os números do topo.
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setEnabled(false);

        BarData barData = new BarData(barDataSet, barDataSet1);
        barData.setValueFormatter(new MyValueFormatter());
        barData.setBarWidth(0.7f);

        barChart.setData(barData);
        barChart.setExtraOffsets(0, 0, 0, 20);
        barChart.setData(barData);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e.getX() == 1) {
                    Toast.makeText(getContext(), "Receitas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Despesas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

}//fim da classe
