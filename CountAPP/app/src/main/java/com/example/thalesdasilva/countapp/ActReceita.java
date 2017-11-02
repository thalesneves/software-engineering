package com.example.thalesdasilva.countapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.app.ViewHelper;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioReceita;
import com.example.thalesdasilva.countapp.entidades.Receita;
import com.example.thalesdasilva.countapp.fragments.VisaoGeral;
import com.example.thalesdasilva.countapp.util.DateUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ThalesdaSilva on 17/09/2017.
 */

public class ActReceita extends AppCompatActivity {

    //Atributos
    private EditText edtReceita;
    private EditText edtValorTransacao;
    private EditText edtData;

    private Spinner spnCategoria;
    private Spinner spnMoeda;
    private Spinner spnFormaRecebimento;

    public ArrayAdapter<String> adpCategoria;
    public ArrayAdapter<String> adpMoeda;
    public ArrayAdapter<String> adpFormaRecebimento;

    private DataBase database;
    private SQLiteDatabase conn;

    private Receita receita;
    private RepositorioReceita repositorioReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_receitas);

        getSupportActionBar().setTitle("Receitas");

        edtReceita = (EditText) ActReceita.this.findViewById(R.id.edtReceita);
        edtValorTransacao = (EditText) ActReceita.this.findViewById(R.id.edtValorTransacao);
        edtData = (EditText) ActReceita.this.findViewById(R.id.edtData);

        spnCategoria = (Spinner) ActReceita.this.findViewById(R.id.spnCategoria);
        spnMoeda = (Spinner) ActReceita.this.findViewById(R.id.spnMoeda);
        spnFormaRecebimento = (Spinner) ActReceita.this.findViewById(R.id.spnFormaRecebimento);

        //Bloqueando o campo IDReceita;
        edtReceita.setEnabled(false);

        adpCategoria = ViewHelper.createArrayAdapter(ActReceita.this, spnCategoria);

        adpCategoria.add("Selecione uma categoria");
        adpCategoria.add(ReceitaArrayAdapter.ALUGUEL);
        adpCategoria.add(ReceitaArrayAdapter.ECONOMIAS_PESSOAIS);
        adpCategoria.add(ReceitaArrayAdapter.EMPRESA);
        adpCategoria.add(ReceitaArrayAdapter.JOGOS_DE_APOSTA);
        adpCategoria.add(ReceitaArrayAdapter.OUTROS);
        adpCategoria.add(ReceitaArrayAdapter.PENSAO);
        adpCategoria.add(ReceitaArrayAdapter.RENDIMENTO_FINANCEIRO);
        adpCategoria.add(ReceitaArrayAdapter.SALARIO);
        adpCategoria.add(ReceitaArrayAdapter.TRABALHO);

        adpMoeda = ViewHelper.createArrayAdapter(ActReceita.this, spnMoeda);

        adpMoeda.add("Selecione uma moeda");
        adpMoeda.add("Real");
        adpMoeda.add("Dólar");
        adpMoeda.add("Euro");
        adpMoeda.add("Outra");

        adpFormaRecebimento = ViewHelper.createArrayAdapter(ActReceita.this, spnFormaRecebimento);

        adpFormaRecebimento.add("Selecione uma forma de recebimento");
        adpFormaRecebimento.add("Dinheiro");
        adpFormaRecebimento.add("Cartão de crédito");
        adpFormaRecebimento.add("Cartão de débito");
        adpFormaRecebimento.add("Cartão internacional");
        adpFormaRecebimento.add("Boleto bancário");
        adpFormaRecebimento.add("Cheque");
        adpFormaRecebimento.add("Outra");

        ActReceita.ExibeDataListener listener = new ExibeDataListener();
        edtData.setOnClickListener(listener);
        edtData.setOnFocusChangeListener(listener);

        //Código que impede de o usuário digitar algo
        edtData.setKeyListener(null);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //ACT_CAD_RECEITA
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(VisaoGeral.Par_Receita))) {
            receita = (Receita) bundle.getSerializable(VisaoGeral.Par_Receita);
            preencheDados();
        } else {
            receita = new Receita();
        }

        try {

            database = new DataBase(ActReceita.this);
            conn = database.getWritableDatabase();

            repositorioReceita = new RepositorioReceita(conn);

            //MessageBox.show(ActReceita.this, "Mensagem", "Conexão criada com sucesso!");

        } catch (SQLException e) {
            MessageBox.show(ActReceita.this, "Erro", "Erro ao criar o Banco de Dados: " + e.getMessage());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            conn.close();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //classe MenuInflater responsável por fazer a ligação do menu
        //com a classe JAVA
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_receitas, menu);

        if (receita.getIdReceita() != 0) {
            menu.getItem(1).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menuSalvarReceita) {
            salvar();
        } else if (id == R.id.menuExcluirReceita) {
            excluir();
        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheDados() {
        edtReceita.setText(String.valueOf(receita.getIdReceita()));

        String receitaString = receita.getNome().toString();

        if (receitaString.equals(ReceitaArrayAdapter.ALUGUEL)) {
            spnCategoria.setSelection(1);
        } else if (receitaString.equals(ReceitaArrayAdapter.ECONOMIAS_PESSOAIS)) {
            spnCategoria.setSelection(2);
        } else if (receitaString.equals(ReceitaArrayAdapter.EMPRESA)) {
            spnCategoria.setSelection(3);
        } else if (receitaString.equals(ReceitaArrayAdapter.JOGOS_DE_APOSTA)) {
            spnCategoria.setSelection(4);
        } else if (receitaString.equals(ReceitaArrayAdapter.OUTROS)) {
            spnCategoria.setSelection(5);
        } else if (receitaString.equals(ReceitaArrayAdapter.PENSAO)) {
            spnCategoria.setSelection(6);
        } else if (receitaString.equals(ReceitaArrayAdapter.RENDIMENTO_FINANCEIRO)) {
            spnCategoria.setSelection(7);
        } else if (receitaString.equals(ReceitaArrayAdapter.SALARIO)) {
            spnCategoria.setSelection(8);
        } else if (receitaString.equals(ReceitaArrayAdapter.TRABALHO)) {
            spnCategoria.setSelection(9);
        }

        edtValorTransacao.setText(String.valueOf(receita.getValorTransacao()));

        String moedaString = receita.getMoeda().toString();

        if (moedaString.equals("Real")) {
            spnMoeda.setSelection(1);
        } else if (moedaString.equals("Dólar")) {
            spnMoeda.setSelection(2);
        } else if (moedaString.equals("Euro")) {
            spnMoeda.setSelection(3);
        } else if (moedaString.equals("Outra")) {
            spnMoeda.setSelection(4);
        }

        String formaRecebimentoString = receita.getFormaRecebimento().toString();

        if (formaRecebimentoString.equals("Dinheiro")) {
            spnFormaRecebimento.setSelection(1);
        } else if (formaRecebimentoString.equals("Cartão de crédito")) {
            spnFormaRecebimento.setSelection(2);
        } else if (formaRecebimentoString.equals("Cartão de débito")) {
            spnFormaRecebimento.setSelection(3);
        } else if (formaRecebimentoString.equals("Cartão internacional")) {
            spnFormaRecebimento.setSelection(4);
        } else if (formaRecebimentoString.equals("Boleto bancário")) {
            spnFormaRecebimento.setSelection(5);
        } else if (formaRecebimentoString.equals("Cheque")) {
            spnFormaRecebimento.setSelection(6);
        } else if (formaRecebimentoString.equals("Outra")) {
            spnFormaRecebimento.setSelection(7);
        }

        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(receita.getDate());

        edtData.setText(dt);
    }

    private void salvar() {

        //Pego o valor 'String' do Spinner
        String categoria = String.valueOf(spnCategoria.getSelectedItem());

        //Pego a posição do Spinner
        Integer categoria2 = spnCategoria.getSelectedItemPosition();

        String valor = edtValorTransacao.getText().toString();

        String moeda = String.valueOf(spnMoeda.getSelectedItem());
        Integer moeda2 = spnMoeda.getSelectedItemPosition();

        String formaRecebimento = String.valueOf(spnFormaRecebimento.getSelectedItem());
        Integer formaRecebimento2 = spnFormaRecebimento.getSelectedItemPosition();

        String data = edtData.getText().toString();

        try {

            if (!(categoria2 == 0) && !valor.trim().isEmpty() && !(moeda2 == 0) && !(formaRecebimento2 == 0) && !data.trim().isEmpty()) {

                DataBase dataBase = new DataBase(ActReceita.this);
                Long fkIdUuario = Long.parseLong(dataBase.buscarPerfil(ActLogin.nome).get(0));

                receita.setNome(categoria);
                receita.setValorTransacao(new BigDecimal(edtValorTransacao.getText().toString()));
                receita.setMoeda(moeda);
                receita.setFormaRecebimento(formaRecebimento);
                receita.setFKIDUsuario(fkIdUuario);

//                Toast.makeText(ActReceita.this, "FK: " + fkIdUuario, Toast.LENGTH_LONG).show();

                if (receita.getIdReceita() == 0) {
                    AlertDialog.Builder a = new AlertDialog.Builder(ActReceita.this);
                    a.setTitle("Mensagem");
                    a.setMessage("Receita salva com sucesso!");
                    a.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repositorioReceita.inserir(receita);
                            finish();
                        }
                    });
                    a.show();
                } else {
                    AlertDialog.Builder a = new AlertDialog.Builder(ActReceita.this);
                    a.setTitle("Mensagem");
                    a.setMessage("Receita salva com sucesso!");
                    a.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repositorioReceita.alterar(receita);
                            finish();
                        }
                    });
                    a.show();
                }

            } else {
                MessageBox.show(ActReceita.this, "Alerta", "Há Dados em Branco, por favor preencha-os !!!");
            }

        } catch (Exception e) {
            MessageBox.show(ActReceita.this, "Erro", "Erro ao salvar os dados: " + e.getMessage());
        }

    }

    private void excluir() {

        try {

            AlertDialog.Builder a = new AlertDialog.Builder(ActReceita.this);
            a.setTitle("Mensagem");
            a.setMessage("Deseja realmente excluir?");
            a.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    repositorioReceita.excluir(receita.getIdReceita());
                    finish();
                }
            });
            a.setNegativeButton("NÃO", null);
            a.show();

        } catch (Exception e) {
            MessageBox.show(ActReceita.this, "Erro", "Erro ao excluir os dados: " + e.getMessage());
        }

    }

    private void exibeData() {

        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(ActReceita.this, R.style.ReceitasWidth,
                new SelecionaDataListener(), ano, mes, dia);

        dlg.show();

    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener {

        @Override
        public void onClick(View v) {
            exibeData();
        }

        //O objeto 'View v', vai receber a referência do objeto que está disparando o evento edtDatasEspeciais
        //O objeto 'boolean hasFocus' ele vai definir se o componente tem o foco ou não
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (hasFocus) {
                exibeData();
            }

        }

    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            //vai auxiliar e já criar a instância do objeto da classe Calendar
            //e já vai retornar a data do sistema por padrão
            String dt = DateUtils.dateTostring(year, month, dayOfMonth);
            Date date = DateUtils.getDate(year, month, dayOfMonth);

            edtData.setText(dt);

            receita.setDate(date);

        }

    }

}//fim da classe
