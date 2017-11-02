package com.example.thalesdasilva.countapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioDespesa;
import com.example.thalesdasilva.countapp.entidades.Despesa;

import java.util.ArrayList;

/**
 * Created by ThalesdaSilva on 12/10/2017.
 */

public class ActVisaoGeralDespesa extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //Atributos
    private FiltraDados filtraDados;

    private ListView lstDespesas;
    private ArrayAdapter<Despesa> adpDespesas;

    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioDespesa repositorioDespesa;

    public static final String Par_Despesa = "despesa";

    public Long IDPerfil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_visao_geral_despesas);

        getSupportActionBar().setTitle("Visão Geral Despesas");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        lstDespesas = (ListView) findViewById(R.id.lstDespesas);

        lstDespesas.setOnItemClickListener(ActVisaoGeralDespesa.this);

        try {

            database = new DataBase(ActVisaoGeralDespesa.this);
            conn = database.getWritableDatabase();

            repositorioDespesa = new RepositorioDespesa(conn);

            ArrayList<String> dados = database.buscarPerfil(ActLogin.nome);

            IDPerfil = Long.parseLong(dados.get(0));

            adpDespesas = database.buscaDespesasPorIDUsuario(ActVisaoGeralDespesa.this, IDPerfil);

            lstDespesas.setAdapter(adpDespesas);

            filtraDados = new FiltraDados(adpDespesas);

            //MessageBox.show(ActVisaoGeralReceita.this, "Mensagem", "Conexão criada com sucesso!");

        } catch (SQLException e) {
            MessageBox.show(ActVisaoGeralDespesa.this, "Erro", "Erro ao criar o Banco de Dados: " + e.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(ActVisaoGeralDespesa.this, ActDespesa.class);
        startActivityForResult(it, 0);
    }

    //Método que faz o botão 'voltar' funcionar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    //requestCode = vai identificar a janela que foi chamada.
    //resultCode = se o resultado da chamada foi bem sucedido ou não.
    //Intent data = caso haja algum tipo de parâmetro que foi passado pela activity que foi chamada.
    //É chamada sempre quando uma activity(janela) é finalizada, para assim atualizar os dados.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Faz uma nova consulta.
        adpDespesas = database.buscaDespesasPorIDUsuario(ActVisaoGeralDespesa.this, IDPerfil);

        filtraDados.setArrayAdapter(adpDespesas);

        //Atualiza o ListView.
        lstDespesas.setAdapter(adpDespesas);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

        Despesa despesa = adpDespesas.getItem(position);
        Intent itDespesa = new Intent(ActVisaoGeralDespesa.this, ActDespesa.class);
        itDespesa.putExtra(Par_Despesa, despesa);
        startActivityForResult(itDespesa, 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_visao_geral_despesas, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.icMenuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.w("myApp", "onQueryTextSubmit ");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.w("myApp", "onQueryTextChange ");
                adpDespesas.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //Método que filtra o texto enquanto o usuário digita
    private class FiltraDados implements TextWatcher {

        //Atributo pra receber o valor
        private ArrayAdapter<Despesa> arrayAdapter;

        //Construtor que recebe o objeto
        private FiltraDados(ArrayAdapter<Despesa> arrayAdapter) {
            this.arrayAdapter = arrayAdapter;
        }

        public void setArrayAdapter(ArrayAdapter<Despesa> arrayAdapter) {
            this.arrayAdapter = arrayAdapter;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Método que filtra o que o usuário está digitando
            //passa como parâmetro o CharSequence = Sequência de caracteres
            arrayAdapter.getFilter().filter(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }

}//fim da classe
