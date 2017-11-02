package com.example.thalesdasilva.countapp;

import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioUsuario;
import com.example.thalesdasilva.countapp.entidades.Usuario;

public class ActCadastro extends AppCompatActivity {

    //Atributos
    private EditText edtEmail;
    private EditText edtNomeUsuario;
    private EditText edtSenha;

    private Button btnCadastrar;
    private Button btnVoltar;

    private DataBase database;
    private SQLiteDatabase conn;

    private Usuario usuario = new Usuario();
    private RepositorioUsuario repositorioUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro);

//        getSupportActionBar().setTitle("Cadastro");
        getSupportActionBar().hide();

        //Recuperando a referência
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtNomeUsuario = (EditText) findViewById(R.id.edtNome);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {

            database = new DataBase(ActCadastro.this);
            conn = database.getWritableDatabase();

            repositorioUsuario = new RepositorioUsuario(conn);

            //MessageBox.show(ActReceita.this, "Mensagem", "Conexão criada com sucesso!");

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

    private void salvar() {

        String email = edtEmail.getText().toString();
        String nomeUsuario = edtNomeUsuario.getText().toString();
        String senha = edtSenha.getText().toString();

        try {

            if (!email.trim().isEmpty() && !nomeUsuario.trim().isEmpty() && !senha.trim().isEmpty()) {

                if (!(senha.trim().length() < 8)) {

                    usuario.setEmail(edtEmail.getText().toString());
                    usuario.setNomeUsuario(edtNomeUsuario.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());

                    boolean dados = database.buscarNomeExistente(nomeUsuario);

                    if (dados == true) {
                        MessageBox.show(ActCadastro.this, "Alerta", "Já existe um usuário com esse nome !!!");
                    } else {
                        AlertDialog.Builder a = new AlertDialog.Builder(ActCadastro.this);
                        a.setTitle("Mensagem");
                        a.setMessage("Usuário salvo com sucesso!");
                        a.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                repositorioUsuario.inserir(usuario);
                                finish();
                            }
                        });
                        a.show();
                    }

                } else {
                    MessageBox.show(ActCadastro.this, "Alerta", "A senha deve ter no mínimo 8 caracteres !!!");
                }

            } else {
                MessageBox.show(ActCadastro.this, "Alerta", "Há Dados em Branco, por favor preencha-os !!!");
            }

        } catch (Exception e) {
            MessageBox.show(ActCadastro.this, "Erro", "Erro ao salvar os dados: " + e.getMessage());
        }

    }

}//fim da classe
