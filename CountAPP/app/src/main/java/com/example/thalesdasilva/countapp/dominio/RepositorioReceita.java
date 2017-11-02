package com.example.thalesdasilva.countapp.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.ReceitaArrayAdapter;
import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.entidades.Receita;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ThalesdaSilva on 11/07/2017.
 */

public class RepositorioReceita {

    private SQLiteDatabase conn;

    public RepositorioReceita(SQLiteDatabase conn) {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Receita receita) {

        ContentValues values = new ContentValues();

        values.put(Receita.NOME, receita.getNome());
        values.put(Receita.VALOR_TRANSACAO, String.valueOf(receita.getValorTransacao()));
        values.put(Receita.MOEDA, receita.getMoeda());
        values.put(Receita.FORMA_RECEBIMENTO, receita.getFormaRecebimento());
        values.put(Receita.DATA, receita.getDate().getTime());
        values.put(Receita.FK, receita.getFKIDUsuario().intValue());

        return values;

    }

    public void inserir(Receita receita) {

        ContentValues values = preencheContentValues(receita);

        conn.insertOrThrow(Receita.TABELA, null, values);

    }

    public void alterar(Receita receita) {

        ContentValues values = preencheContentValues(receita);

        conn.update(Receita.TABELA, values, "IDReceita = ?", new String[]{String.valueOf(receita.getIdReceita())});

    }

    public void excluir(Long id) {

        conn.delete(Receita.TABELA, "IDReceita = ?", new String[]{String.valueOf(id)});

    }

    public ReceitaArrayAdapter buscaReceitas(Context context) {

        ReceitaArrayAdapter adpReceitas = new ReceitaArrayAdapter(context, R.layout.act_item_receitas);

        Cursor cursor = conn.query(Receita.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Receita receita = new Receita();

                receita.setIdReceita(cursor.getLong(cursor.getColumnIndex(receita.ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(receita.NOME)));
                receita.setValorTransacao(new BigDecimal(cursor.getString(cursor.getColumnIndex(receita.VALOR_TRANSACAO))));
                receita.setMoeda(cursor.getString(cursor.getColumnIndex(receita.MOEDA)));
                receita.setFormaRecebimento(cursor.getString(cursor.getColumnIndex(receita.FORMA_RECEBIMENTO)));
                receita.setDate(new Date(cursor.getLong(cursor.getColumnIndex(receita.DATA))));

                adpReceitas.add(receita);

            } while (cursor.moveToNext());
        }

        return adpReceitas;

    }

}//fim da classe