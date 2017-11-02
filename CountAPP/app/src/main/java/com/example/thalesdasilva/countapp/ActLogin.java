package com.example.thalesdasilva.countapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.database.DataBase;

public class ActLogin extends AppCompatActivity {

    //Atributos
    private EditText edtNome;
    private EditText edtSenha;

    public static String nome;

    private Button btnLogin;
    private Button btnCadastro;
    private Button btnEsqueceuASenha;

    private DataBase dataBase = new DataBase(ActLogin.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

//        getSupportActionBar().setTitle("Login");
        getSupportActionBar().hide();

        //Recuperando a referência
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCadastro = (Button) findViewById(R.id.btnCadastro);
        btnEsqueceuASenha = (Button) findViewById(R.id.btnEsqueceuASenha);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });

        btnEsqueceuASenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActLogin.this, ActEsqueceuSenha.class);
                startActivity(it);
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActLogin.this, ActCadastro.class);
                startActivity(it);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }

    private void validarLogin() {

        nome = edtNome.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        String pass = dataBase.buscarNome(nome);

        if (!nome.isEmpty() && !senha.isEmpty()) {
            if (senha.equals(pass)) {
                Intent it = new Intent(ActLogin.this, MainActivity.class);
//                it.putExtra("edtNome", nome);
                startActivity(it);
            } else {
                MessageBox.show(ActLogin.this, "Erro", "Nome de Usuário ou Senha incorretos!!!");
            }
        } else {
            MessageBox.show(ActLogin.this, "Alerta", "Há Dados em Branco, por favor preencha-os !!!");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        edtSenha.setText(null);
    }

}//fim da classe
