package com.example.thalesdasilva.countapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioUsuario;
import com.example.thalesdasilva.countapp.entidades.Usuario;

import java.util.ArrayList;

/**
 * Created by ThalesdaSilva on 20/10/2017.
 */

public class ActEsqueceuSenha extends AppCompatActivity implements View.OnClickListener {

    //Aributos
    private Button btnEmail;
    private Button btnVoltar;

    private EditText edtEmail;

    private DataBase database;
    private SQLiteDatabase conn;

    public RepositorioUsuario repositorioUsuario;
    public Usuario usuario = new Usuario();

    public Long IDPerfil = null;

    public Long numeroRandomico = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_esqueceu_senha);

        getSupportActionBar().hide();

        btnEmail = (Button) findViewById(R.id.btnEmail);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Initializing the views
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        //Adding click listener
        btnEmail.setOnClickListener(ActEsqueceuSenha.this);

        numeroRandomico = (long) (Math.random() * 10000000000L);

        try {

            database = new DataBase(ActEsqueceuSenha.this);
            conn = database.getWritableDatabase();

            repositorioUsuario = new RepositorioUsuario(conn);

//            MessageBox.show(ActEsqueceuSenha.this, "Mensagem", "Conexão criada com sucesso!");

        } catch (SQLException e) {
            MessageBox.show(ActEsqueceuSenha.this, "Erro", "Erro ao criar o Banco de Dados: " + e.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            conn.close();
        }
    }

    public String getMensagem() {

        String email = edtEmail.getText().toString();

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("\nFoi solicitado o envio da senha em nosso aplicativo para esse email: " + email);
        mensagem.append("\nSenha: " + numeroRandomico);

        return mensagem.toString();

    }

    @Override
    public void onClick(View v) {

        String email = edtEmail.getText().toString();

        if (!email.trim().isEmpty()) {

            boolean dados = database.buscarEmailExistente(email);

            if (!hasInternetConnection()) {
                Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dados == true) {
                ArrayList<String> dadosPerfil = database.buscarPerfilPorEmail(email);

                IDPerfil = Long.parseLong(dadosPerfil.get(0));

                usuario.setIDUsuario(IDPerfil);
                usuario.setEmail(email);
                usuario.setNomeUsuario(dadosPerfil.get(2).toString());
                usuario.setSenha(numeroRandomico.toString());

                repositorioUsuario.alterar(usuario);

                new SendMailAsynTask(ActEsqueceuSenha.this, email, getMensagem()).execute();
                //call send mail  cunstructor asyntask by  sending perameter
            } else {
                MessageBox.show(ActEsqueceuSenha.this, "Alerta", "Não existe esse email cadastrado em nossa base de dados !!!");
            }

        } else {
            MessageBox.show(ActEsqueceuSenha.this, "Alerta", "Campo do email em branco, por favor preencha-o !!!");
        }

    }

    /* Método para verificar a existência de conexão com a internet */
    public boolean hasInternetConnection() {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
//            Toast.makeText(context, "MapsFragment hasInternetConnection: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

}//fim da classe
