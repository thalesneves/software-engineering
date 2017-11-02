package com.example.thalesdasilva.countapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.app.ViewHelper;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioDespesa;
import com.example.thalesdasilva.countapp.entidades.Despesa;
import com.example.thalesdasilva.countapp.fragments.VisaoGeral;
import com.example.thalesdasilva.countapp.util.DateUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ThalesdaSilva on 20/09/2017.
 */

public class ActDespesa extends AppCompatActivity {

    //Atributos
    private EditText edtDespesa;
    private EditText edtValorTransacao;
    private EditText edtData;

    private Spinner spnCategoria;
    private Spinner spnMoeda;
    private Spinner spnFormaPagamento;

    public ArrayAdapter<String> adpCategoria;
    public ArrayAdapter<String> adpMoeda;
    public ArrayAdapter<String> adpFormaPagamento;

    private DataBase database;
    private SQLiteDatabase conn;

    private Despesa despesa;
    private RepositorioDespesa repositorioDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_despesas);

        getSupportActionBar().setTitle("Despesas");

        edtDespesa = (EditText) ActDespesa.this.findViewById(R.id.edtDespesa);
        edtValorTransacao = (EditText) ActDespesa.this.findViewById(R.id.edtValorTransacao);
        edtData = (EditText) ActDespesa.this.findViewById(R.id.edtData);

        spnCategoria = (Spinner) ActDespesa.this.findViewById(R.id.spnCategoria);
        spnMoeda = (Spinner) ActDespesa.this.findViewById(R.id.spnMoeda);
        spnFormaPagamento = (Spinner) ActDespesa.this.findViewById(R.id.spnFormaPagamento);

        //Bloqueando o campo IDDespesa;
        edtDespesa.setEnabled(false);

        adpCategoria = ViewHelper.createArrayAdapter(ActDespesa.this, spnCategoria);

        adpCategoria.add("Selecione uma categoria");
        adpCategoria.add(DespesaArrayAdapter.AGUA);
        adpCategoria.add(DespesaArrayAdapter.ALIMENTACAO);
        adpCategoria.add(DespesaArrayAdapter.ALUGUEL);
        adpCategoria.add(DespesaArrayAdapter.ANIMAL);
        adpCategoria.add(DespesaArrayAdapter.CALCADOS);
        adpCategoria.add(DespesaArrayAdapter.CIGARROS);
        adpCategoria.add(DespesaArrayAdapter.COMBUSTIVEL);
        adpCategoria.add(DespesaArrayAdapter.COMPRAS);
        adpCategoria.add(DespesaArrayAdapter.CONSERTOS);
        adpCategoria.add(DespesaArrayAdapter.CRIANCAS);
        adpCategoria.add(DespesaArrayAdapter.ELETRONICOS);
        adpCategoria.add(DespesaArrayAdapter.ENTRETENIMENTO);
        adpCategoria.add(DespesaArrayAdapter.FAMILIA);
        adpCategoria.add(DespesaArrayAdapter.GAMES);
        adpCategoria.add(DespesaArrayAdapter.GAS);
        adpCategoria.add(DespesaArrayAdapter.GASTOS_PESSOAIS);
        adpCategoria.add(DespesaArrayAdapter.LIVROS);
        adpCategoria.add(DespesaArrayAdapter.LUZ);
        adpCategoria.add(DespesaArrayAdapter.OUTROS);
        adpCategoria.add(DespesaArrayAdapter.PENSAO);
        adpCategoria.add(DespesaArrayAdapter.PERDA_APOSTA);
        adpCategoria.add(DespesaArrayAdapter.PRESENTE);
        adpCategoria.add(DespesaArrayAdapter.RELOGIO);
        adpCategoria.add(DespesaArrayAdapter.ROUPAS);
        adpCategoria.add(DespesaArrayAdapter.SAUDE);
        adpCategoria.add(DespesaArrayAdapter.SEGURO);
        adpCategoria.add(DespesaArrayAdapter.TELEFONE);
        adpCategoria.add(DespesaArrayAdapter.TRANSPORTE);
        adpCategoria.add(DespesaArrayAdapter.VEICULO);
        adpCategoria.add(DespesaArrayAdapter.VIAGEM);

        adpMoeda = ViewHelper.createArrayAdapter(ActDespesa.this, spnMoeda);

        adpMoeda.add("Selecione uma moeda");
        adpMoeda.add("Real");
        adpMoeda.add("Dólar");
        adpMoeda.add("Euro");
        adpMoeda.add("Outra");

        adpFormaPagamento = ViewHelper.createArrayAdapter(ActDespesa.this, spnFormaPagamento);

        adpFormaPagamento.add("Selecione uma forma de pagamento");
        adpFormaPagamento.add("Dinheiro");
        adpFormaPagamento.add("Cartão de crédito");
        adpFormaPagamento.add("Cartão de débito");
        adpFormaPagamento.add("Cartão internacional");
        adpFormaPagamento.add("Boleto bancário");
        adpFormaPagamento.add("Cheque");
        adpFormaPagamento.add("Vale refeição");
        adpFormaPagamento.add("Outra");

        ActDespesa.ExibeDataListener listener = new ActDespesa.ExibeDataListener();
        edtData.setOnClickListener(listener);
        edtData.setOnFocusChangeListener(listener);

        //Código que impede de o usuário digitar algo
        edtData.setKeyListener(null);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //ACT_CAD_DESPESA
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(VisaoGeral.Par_Despesa))) {
            despesa = (Despesa) bundle.getSerializable(VisaoGeral.Par_Despesa);
            preencheDados();
        } else {
            despesa = new Despesa();
        }

        try {

            database = new DataBase(ActDespesa.this);
            conn = database.getWritableDatabase();

            repositorioDespesa = new RepositorioDespesa(conn);

            //MessageBox.show(ActDespesa.this, "Mensagem", "Conexão criada com sucesso!");

        } catch (SQLException e) {
            MessageBox.show(this, "Erro", "Erro ao criar o Banco de Dados: " + e.getMessage());
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
        inflater.inflate(R.menu.menu_act_despesas, menu);

        if (despesa.getIDDespesa() != 0) {
            menu.getItem(1).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menuSalvarDespesa) {
            salvar();
        } else if (id == R.id.menuExcluirDespesa) {
            excluir();
        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheDados() {
        edtDespesa.setText(String.valueOf(despesa.getIDDespesa()));

        String despesaString = despesa.getNome().toString();

        if (despesaString.equals(DespesaArrayAdapter.AGUA)) {
            spnCategoria.setSelection(1);
        } else if (despesaString.equals(DespesaArrayAdapter.ALIMENTACAO)) {
            spnCategoria.setSelection(2);
        } else if (despesaString.equals(DespesaArrayAdapter.ALUGUEL)) {
            spnCategoria.setSelection(3);
        } else if (despesaString.equals(DespesaArrayAdapter.ANIMAL)) {
            spnCategoria.setSelection(4);
        } else if (despesaString.equals(DespesaArrayAdapter.CALCADOS)) {
            spnCategoria.setSelection(5);
        } else if (despesaString.equals(DespesaArrayAdapter.CIGARROS)) {
            spnCategoria.setSelection(6);
        } else if (despesaString.equals(DespesaArrayAdapter.COMBUSTIVEL)) {
            spnCategoria.setSelection(7);
        } else if (despesaString.equals(DespesaArrayAdapter.COMPRAS)) {
            spnCategoria.setSelection(8);
        } else if (despesaString.equals(DespesaArrayAdapter.CONSERTOS)) {
            spnCategoria.setSelection(9);
        } else if (despesaString.equals(DespesaArrayAdapter.CRIANCAS)) {
            spnCategoria.setSelection(10);
        } else if (despesaString.equals(DespesaArrayAdapter.ELETRONICOS)) {
            spnCategoria.setSelection(11);
        } else if (despesaString.equals(DespesaArrayAdapter.ENTRETENIMENTO)) {
            spnCategoria.setSelection(12);
        } else if (despesaString.equals(DespesaArrayAdapter.FAMILIA)) {
            spnCategoria.setSelection(13);
        } else if (despesaString.equals(DespesaArrayAdapter.GAMES)) {
            spnCategoria.setSelection(14);
        } else if (despesaString.equals(DespesaArrayAdapter.GAS)) {
            spnCategoria.setSelection(15);
        } else if (despesaString.equals(DespesaArrayAdapter.GASTOS_PESSOAIS)) {
            spnCategoria.setSelection(16);
        } else if (despesaString.equals(DespesaArrayAdapter.LIVROS)) {
            spnCategoria.setSelection(17);
        }else if (despesaString.equals(DespesaArrayAdapter.LUZ)) {
            spnCategoria.setSelection(18);
        } else if (despesaString.equals(DespesaArrayAdapter.OUTROS)) {
            spnCategoria.setSelection(19);
        } else if (despesaString.equals(DespesaArrayAdapter.PENSAO)) {
            spnCategoria.setSelection(20);
        } else if (despesaString.equals(DespesaArrayAdapter.PERDA_APOSTA)) {
            spnCategoria.setSelection(21);
        } else if (despesaString.equals(DespesaArrayAdapter.PRESENTE)) {
            spnCategoria.setSelection(22);
        } else if (despesaString.equals(DespesaArrayAdapter.RELOGIO)) {
            spnCategoria.setSelection(23);
        } else if (despesaString.equals(DespesaArrayAdapter.ROUPAS)) {
            spnCategoria.setSelection(24);
        } else if (despesaString.equals(DespesaArrayAdapter.SAUDE)) {
            spnCategoria.setSelection(25);
        } else if (despesaString.equals(DespesaArrayAdapter.SEGURO)) {
            spnCategoria.setSelection(26);
        } else if (despesaString.equals(DespesaArrayAdapter.TELEFONE)) {
            spnCategoria.setSelection(27);
        } else if (despesaString.equals(DespesaArrayAdapter.TRANSPORTE)) {
            spnCategoria.setSelection(28);
        } else if (despesaString.equals(DespesaArrayAdapter.VEICULO)) {
            spnCategoria.setSelection(29);
        } else if (despesaString.equals(DespesaArrayAdapter.VIAGEM)) {
            spnCategoria.setSelection(30);
        }

        edtValorTransacao.setText(String.valueOf(despesa.getValorTransacao()));

        String moedaString = despesa.getMoeda().toString();

        if (moedaString.equals("Real")) {
            spnMoeda.setSelection(1);
        } else if (moedaString.equals("Dólar")) {
            spnMoeda.setSelection(2);
        } else if (moedaString.equals("Euro")) {
            spnMoeda.setSelection(3);
        } else if (moedaString.equals("Outra")) {
            spnMoeda.setSelection(4);
        }

        String formaPagamentoString = despesa.getFormaPagamento().toString();

        if (formaPagamentoString.equals("Dinheiro")) {
            spnFormaPagamento.setSelection(1);
        } else if (formaPagamentoString.equals("Cartão de crédito")) {
            spnFormaPagamento.setSelection(2);
        } else if (formaPagamentoString.equals("Cartão de débito")) {
            spnFormaPagamento.setSelection(3);
        } else if (formaPagamentoString.equals("Cartão internacional")) {
            spnFormaPagamento.setSelection(4);
        } else if (formaPagamentoString.equals("Boleto bancário")) {
            spnFormaPagamento.setSelection(5);
        } else if (formaPagamentoString.equals("Cheque")) {
            spnFormaPagamento.setSelection(6);
        } else if (formaPagamentoString.equals("Vale refeição")) {
            spnFormaPagamento.setSelection(7);
        } else if (formaPagamentoString.equals("Outra")) {
            spnFormaPagamento.setSelection(8);
        }

        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(despesa.getDate());

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

        String formaPagamento = String.valueOf(spnFormaPagamento.getSelectedItem());
        Integer formaPagamento2 = spnFormaPagamento.getSelectedItemPosition();

        String data = edtData.getText().toString();

        try {

            if (!(categoria2 == 0) && !valor.trim().isEmpty() && !(moeda2 == 0) && !(formaPagamento2 == 0) && !data.trim().isEmpty()) {

                DataBase dataBase = new DataBase(ActDespesa.this);
                Long fkIdUuario = Long.parseLong(dataBase.buscarPerfil(ActLogin.nome).get(0));

                despesa.setNome(categoria);
                despesa.setValorTransacao(new BigDecimal(edtValorTransacao.getText().toString()));
                despesa.setMoeda(moeda);
                despesa.setFormaPagamento(formaPagamento);
                despesa.setFKIDUsuario(fkIdUuario);

                if (despesa.getIDDespesa() == 0) {
                    AlertDialog.Builder a = new AlertDialog.Builder(ActDespesa.this);
                    a.setTitle("Mensagem");
                    a.setMessage("Despesa salva com sucesso!");
                    a.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repositorioDespesa.inserir(despesa);
                            finish();
                        }
                    });
                    a.show();
                } else {
                    AlertDialog.Builder a = new AlertDialog.Builder(ActDespesa.this);
                    a.setTitle("Mensagem");
                    a.setMessage("Despesa salva com sucesso!");
                    a.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repositorioDespesa.alterar(despesa);
                            finish();
                        }
                    });
                    a.show();
                }

            } else {
                MessageBox.show(ActDespesa.this, "Alerta", "Há Dados em Branco, por favor preencha-os !!!");
            }

        } catch (Exception e) {
            MessageBox.show(ActDespesa.this, "Erro", "Erro ao salvar os dados: " + e.getMessage());
        }

    }

    private void excluir() {

        try {

            AlertDialog.Builder a = new AlertDialog.Builder(ActDespesa.this);
            a.setTitle("Mensagem");
            a.setMessage("Deseja realmente excluir?");
            a.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    repositorioDespesa.excluir(despesa.getIDDespesa());
                    finish();
                }
            });
            a.setNegativeButton("NÃO", null);
            a.show();

        } catch (Exception e) {
            MessageBox.show(ActDespesa.this, "Erro", "Erro ao excluir os dados: " + e.getMessage());
        }

    }

    private void exibeData() {

        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(ActDespesa.this, R.style.DespesasWidth,
                new ActDespesa.SelecionaDataListener(), ano, mes, dia);

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

            despesa.setDate(date);

        }

    }

}//fim da classe
