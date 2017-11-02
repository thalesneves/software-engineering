package com.example.thalesdasilva.countapp.fragments;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thalesdasilva.countapp.ActLogin;
import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioUsuario;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Conta extends Fragment {

    //Atributos
    public TextView txtTotalReceita;
    public TextView txtTotalDespesa;
    public TextView txtTotalCarteira;

    private DataBase database;
    private SQLiteDatabase conn;

    public RepositorioUsuario repositorioUsuario;

    Long IDPerfil = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Contas");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_conta, container, false);

        txtTotalReceita = (TextView) view.findViewById(R.id.txtTotalReceita);
        txtTotalDespesa = (TextView) view.findViewById(R.id.txtTotalDespesa);
        txtTotalCarteira = (TextView) view.findViewById(R.id.txtTotalCarteira);

        try {

            database = new DataBase(getContext());
            conn = database.getWritableDatabase();

            repositorioUsuario = new RepositorioUsuario(conn);

            ArrayList<String> dados = database.buscarPerfil(ActLogin.nome);

            IDPerfil = Long.parseLong(dados.get(0));

//            MessageBox.show(view.getContext(), "Mensagem", "Conexão criada com sucesso!");

        } catch (SQLException e) {
            MessageBox.show(getContext(), "Erro", "Erro ao criar o Banco de Dados: " + e.getMessage());
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabPlus);
        fab.show();
        atualizarReceitasEDespesas();
    }

    private void atualizarReceitasEDespesas() {
        Float dadosReceitas = database.somarReceita(IDPerfil);
        Float dadosDespesas = database.somarDespesa(IDPerfil);

        Float somaReceitasEDespesas = dadosReceitas - dadosDespesas;

        DecimalFormat df = new DecimalFormat(".##");

        String resultFReceita = String.format(String.valueOf(df.format(dadosReceitas))).replace(",", ".");
        String resultFDespesa = String.format(String.valueOf(df.format(dadosDespesas))).replace(",", ".");
        String resultSomaFDespesa = String.format(String.valueOf(df.format(somaReceitasEDespesas))).replace(",", ".");

        txtTotalReceita.setText("+" + String.valueOf(resultFReceita));
        txtTotalDespesa.setText("-" + String.valueOf(resultFDespesa));
        txtTotalCarteira.setText(String.valueOf(resultSomaFDespesa));
    }

}//fim da classe
