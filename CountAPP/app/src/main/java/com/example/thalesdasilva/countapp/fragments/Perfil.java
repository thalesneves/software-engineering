package com.example.thalesdasilva.countapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.thalesdasilva.countapp.ActLogin;
import com.example.thalesdasilva.countapp.ActReceita;
import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioUsuario;
import com.example.thalesdasilva.countapp.entidades.Usuario;

import java.util.ArrayList;

/**
 * Created by ThalesdaSilva on 17/09/2017.
 */

public class Perfil extends Fragment {

    //Atributos
    private EditText edtEmail;
    private EditText edtNome;
    private EditText edtSenha;

    public String emailPerfil;
    public String nomePerfil;
    public String senhaPerfil;

    private DataBase database;
    private SQLiteDatabase conn;

    private RepositorioUsuario repositorioUsuario;

    Usuario usuario = new Usuario();

    Long IDPerfil = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_perfil, container, false);

        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtNome = (EditText) view.findViewById(R.id.edtNome);
        edtSenha = (EditText) view.findViewById(R.id.edtSenha);

        edtEmail.setEnabled(false);
        edtNome.setEnabled(false);
        edtSenha.setEnabled(false);

        try {

            database = new DataBase(getContext());
            conn = database.getWritableDatabase();

            repositorioUsuario = new RepositorioUsuario(conn);

//            Bundle bundle = getActivity().getIntent().getExtras();
//            nomeBundle = bundle.getString("edtNome");

            ArrayList<String> dados = database.buscarPerfil(ActLogin.nome);

            IDPerfil = Long.parseLong(dados.get(0));
            emailPerfil = String.valueOf(dados.get(1)).toString();
            nomePerfil = String.valueOf(dados.get(2)).toString();
            senhaPerfil = String.valueOf(dados.get(3)).toString();

            edtEmail.setText(emailPerfil);
            edtNome.setText(nomePerfil);
            edtSenha.setText(senhaPerfil);

            //MessageBox.show(view.getContext(), "Mensagem", "Conexão criada com sucesso!");

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Perfil");
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabPlus);
        fab.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_perfil, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().finish();
        } else if (id == R.id.menuSalvarPerfil) {
            salvar();
        } else if (id == R.id.menuAlterarPerfil) {
            alterar();
        } else if (id == R.id.menuExcluirPerfil) {
            excluir();
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {

        String email = edtEmail.getText().toString();
        String nome = edtNome.getText().toString();
        String senha = edtSenha.getText().toString();

        try {
            if (!email.trim().isEmpty() && !nome.trim().isEmpty() && !senha.trim().isEmpty()) {

                if (!(senha.trim().length() < 8)) {

                    usuario.setIDUsuario(IDPerfil);
                    usuario.setEmail(email);
                    usuario.setNomeUsuario(nome);
                    usuario.setSenha(senha);

                    repositorioUsuario.alterar(usuario);
                    MessageBox.show(getContext(), "Mensagem", "Usuário salvo com sucesso !!!");

                    edtEmail.setEnabled(false);
                    edtNome.setEnabled(false);
                    edtSenha.setEnabled(false);

                    ActLogin.nome = nome;
//                Bundle bundle = getActivity().getIntent().getExtras();
//                bundle.putString("edtEmail", email);

                } else {
                    MessageBox.show(getContext(), "Alerta", "A senha deve ter no mínimo 8 caracteres !!!");
                }

            } else {
                MessageBox.show(getContext(), "Alerta", "Há Dados em Branco, por favor preencha-os !!!");
            }

        } catch (Exception e) {
            MessageBox.show(getContext(), "Erro", "Erro ao salvar os dados: " + e.getMessage());
        }

    }

    private void alterar() {
        edtEmail.setEnabled(true);
        edtNome.setEnabled(true);
        edtSenha.setEnabled(true);
    }

    private void excluir() {
        try {
            AlertDialog.Builder a = new AlertDialog.Builder(getContext());
            a.setTitle("Mensagem");
            a.setMessage("Deseja realmente excluir?");
            a.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    repositorioUsuario.excluir(IDPerfil);
                    getActivity().finish();
                }
            });
            a.setNegativeButton("NÃO", null);
            a.show();
        } catch (Exception e) {
            MessageBox.show(getContext(), "Erro", "Erro ao excluir os dados: " + e.getMessage());
        }
    }

}//fim da classe
