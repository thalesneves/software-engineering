package com.example.thalesdasilva.countapp.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.UsuarioArrayAdapter;
import com.example.thalesdasilva.countapp.entidades.Usuario;

/**
 * Created by ThalesdaSilva on 14/10/2017.
 */

public class RepositorioUsuario {

    private SQLiteDatabase conn;

    public String nome;

    public RepositorioUsuario(SQLiteDatabase conn) {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Usuario usuario) {

        ContentValues values = new ContentValues();

        values.put(Usuario.EMAIL, usuario.getEmail());
        values.put(Usuario.NOME_USUARIO, usuario.getNomeUsuario());
        values.put(Usuario.SENHA, usuario.getSenha());

        return values;

    }

    public void inserir(Usuario usuario) {

        ContentValues values = preencheContentValues(usuario);

        conn.insertOrThrow(Usuario.TABELA, null, values);

    }

    public void alterar(Usuario usuario) {

        ContentValues values = preencheContentValues(usuario);

        conn.update(Usuario.TABELA, values, "IDUsuario = ?", new String[]{String.valueOf(usuario.getIDUsuario())});

    }

    public void excluir(Long id) {

        conn.delete(Usuario.TABELA, "IDUsuario = ?", new String[]{String.valueOf(id)});

    }

    public UsuarioArrayAdapter buscaUsuarios(Context context) {

        UsuarioArrayAdapter adpUsuarios = new UsuarioArrayAdapter(context, R.layout.frag_perfil);

        Cursor cursor = conn.query(Usuario.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Usuario usuario = new Usuario();

                if (cursor.getString(cursor.getColumnIndex(usuario.NOME_USUARIO)).equals(nome)) {

                    usuario.setIDUsuario(cursor.getLong(cursor.getColumnIndex(usuario.ID)));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndex(usuario.EMAIL)));
                    usuario.setNomeUsuario(cursor.getString(cursor.getColumnIndex(usuario.NOME_USUARIO)));
                    usuario.setSenha(cursor.getString(cursor.getColumnIndex(usuario.SENHA)));

                    adpUsuarios.add(usuario);
                }

            } while (cursor.moveToNext());
        }

        return adpUsuarios;

    }

}//fim da classe
