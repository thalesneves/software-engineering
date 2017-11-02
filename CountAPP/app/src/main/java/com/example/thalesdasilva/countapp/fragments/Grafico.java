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
import com.example.thalesdasilva.countapp.MyValueFormatter;
import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioUsuario;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThalesdaSilva on 17/09/2017.
 */

public class Grafico extends Fragment {

    private DataBase database;
    private SQLiteDatabase conn;
    public RepositorioUsuario repositorioUsuario;

    Long IDPerfil = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_gerar_grafico, container, false);
        super.onViewCreated(view, savedInstanceState);
        return view;
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
        setupPieChart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPieChart();
    }

    private void setupPieChart() {

        database = new DataBase(getContext());
        conn = database.getWritableDatabase();

        repositorioUsuario = new RepositorioUsuario(conn);

        ArrayList<String> dados = database.buscarPerfil(ActLogin.nome);

        IDPerfil = Long.parseLong(dados.get(0));

        Float dadosReceitas = database.somarReceita(IDPerfil);
        Float dadosDespesas = database.somarDespesa(IDPerfil);

        Float saldo[] = {dadosReceitas, dadosDespesas};
        String receita_despesa[] = {"Receitas", "Despesas"};

        //popular uma list de PieEntries
        List<PieEntry> pieEntries = new ArrayList<>();

        for (float i = 0; i < saldo.length; i++) {
            pieEntries.add(new PieEntry(saldo[(int) i], receita_despesa[(int) i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
//        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //Coloca a divisão em branco.
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(RECEITAS_DESPESAS);
        PieData data = new PieData(dataSet);
        //Muda a cor do texto e tamanho
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new MyValueFormatter());

        PieChart chart = (PieChart) getView().findViewById(R.id.chart);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleRadius(61f);
        chart.setData(data);
        chart.invalidate();

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(3000);
        chart.setAnimation(animation);

//        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
//        anim.setDuration(3000);
//        chart.setAnimation(anim);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (h.getX() == 0) {
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

    public static final int[] RECEITAS_DESPESAS = {
            Color.rgb(46, 147, 91),
            Color.rgb(188, 0, 0)
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_grafico, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().finish();
        } else if (id == R.id.menuGraficoBarra) {

            GraficoBarra graficoBarra = new GraficoBarra();
            Bundle bundle = getActivity().getIntent().getExtras();
            graficoBarra.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, graficoBarra, graficoBarra.getTag()).commit();

            manager.addOnBackStackChangedListener(null);

        }

        return super.onOptionsItemSelected(item);
    }

}//fim da classe
