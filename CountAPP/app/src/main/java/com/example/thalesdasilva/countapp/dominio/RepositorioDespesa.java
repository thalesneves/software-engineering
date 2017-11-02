package com.example.thalesdasilva.countapp.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.DespesaArrayAdapter;
import com.example.thalesdasilva.countapp.entidades.Despesa;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ThalesdaSilva on 11/07/2017.
 */

public class RepositorioDespesa {

    private SQLiteDatabase conn;

    public RepositorioDespesa(SQLiteDatabase conn) {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Despesa despesa) {

        ContentValues values = new ContentValues();

        values.put(Despesa.NOME, despesa.getNome());
        values.put(Despesa.VALOR_TRANSACAO, String.valueOf(despesa.getValorTransacao()));
        values.put(Despesa.MOEDA, despesa.getMoeda());
        values.put(Despesa.FORMA_PAGAMENTO, despesa.getFormaPagamento());
        values.put(Despesa.DATA, despesa.getDate().getTime());
        values.put(Despesa.FK, despesa.getFKIDUsuario().intValue());

        return values;

    }

    public void inserir(Despesa despesa) {

        ContentValues values = preencheContentValues(despesa);

        conn.insertOrThrow(Despesa.TABELA, null, values);

    }

    public void alterar(Despesa despesa) {

        ContentValues values = preencheContentValues(despesa);

        conn.update(Despesa.TABELA, values, "IDDespesa = ?", new String[]{String.valueOf(despesa.getIDDespesa())});

    }

    public void excluir(Long id) {

        conn.delete(Despesa.TABELA, "IDDespesa = ?", new String[]{String.valueOf(id)});

    }

    public DespesaArrayAdapter buscaDespesas(Context context) {

        DespesaArrayAdapter adpDespesas = new DespesaArrayAdapter(context, R.layout.act_item_despesas);

        Cursor cursor = conn.query(Despesa.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Despesa despesa = new Despesa();

                despesa.setIDDespesa(cursor.getLong(cursor.getColumnIndex(despesa.ID)));
                despesa.setNome(cursor.getString(cursor.getColumnIndex(despesa.NOME)));
                despesa.setValorTransacao(new BigDecimal(cursor.getString(cursor.getColumnIndex(despesa.VALOR_TRANSACAO))));
                despesa.setMoeda(cursor.getString(cursor.getColumnIndex(despesa.MOEDA)));
                despesa.setFormaPagamento(cursor.getString(cursor.getColumnIndex(despesa.FORMA_PAGAMENTO)));
                despesa.setDate(new Date(cursor.getLong(cursor.getColumnIndex(despesa.DATA))));

                adpDespesas.add(despesa);

            } while (cursor.moveToNext());
        }

        return adpDespesas;

    }

}//fim da classe