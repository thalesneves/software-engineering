package com.example.thalesdasilva.countapp.database;

/**
 * Created by ThalesdaSilva on 11/07/2017.
 */

//sessão de importação

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.thalesdasilva.countapp.DespesaArrayAdapter;
import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.ReceitaArrayAdapter;
import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.entidades.Despesa;
import com.example.thalesdasilva.countapp.entidades.Receita;
import com.example.thalesdasilva.countapp.entidades.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class DataBase extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private Context context;

    public DataBase(Context context) {
        //passar a referência da classe context
        //nome do banco de dados
        //referente a classe cursor, busca de dados no banco
        //versão do banco de dados
        super(context, "bdcountapp", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(ScriptSQL.getCreateUsuarios());
            db.execSQL(ScriptSQL.getCreateContas());
            db.execSQL(ScriptSQL.getCreateFormasPagamento());
            db.execSQL(ScriptSQL.getCreateReceitas());
            db.execSQL(ScriptSQL.getCreateDespesas());
            db.execSQL(ScriptSQL.getCreateSaldo());
        } catch (SQLiteException e) {
            MessageBox.show(null, "Erro", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String buscarNome(String nomeUsuario) {

        //getReadableDataBase() Somente a leitura do banco.
        //getWritableDatabase() Leitura e Escrita dos dados.
        db = this.getReadableDatabase();
        String query = "select nomeUsuario, senha from usuarios";
        Cursor cursor = db.rawQuery(query, null);
        String a;
        String b = "not found";

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);//nomeUsuario = 0 e senha = 1.

                if (a.equals(nomeUsuario)) {
                    b = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }

        return b;
    }

    public ArrayList<String> buscarPerfil(String nomeUsuario) {

        ArrayList<String> dados = new ArrayList<>();

        db = this.getReadableDatabase();
        String query = "select * from usuarios where nomeUsuario = '" + nomeUsuario + "'";
        Cursor cursor = db.rawQuery(query, null);
        String a;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(2);

                if (a.equals(nomeUsuario)) {
                    dados.add(cursor.getString(0));
                    dados.add(cursor.getString(1));
                    dados.add(cursor.getString(2));
                    dados.add(cursor.getString(3));
                    break;
                }
            } while (cursor.moveToNext());
        }

        return dados;
    }

    public boolean buscarNomeExistente(String nomeUsuario) {

        db = this.getReadableDatabase();
        String query = "select nomeUsuario from usuarios where nomeUsuario = '" + nomeUsuario + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                if (query != null) {
                    return true;
                }
            } while (cursor.moveToNext());
        }

        return false;
    }

    public ReceitaArrayAdapter buscaReceitasPorIDUsuario(Context context, Long idUsuario) {

        ReceitaArrayAdapter adpReceitas = new ReceitaArrayAdapter(context, R.layout.act_item_receitas);

        String query = "select * from receitas where FKIDUsuario = " + idUsuario;
//        String query = "select IDReceita, nome, valorTransacao, moeda, data, FKIDUsuario from receitas inner join usuarios on usuarios.IDUsuario = " + idUsuario;
//        String query = "select IDReceita, nome, valorTransacao, moeda, data, FKIDUsuario from receitas ";
        Cursor cursor = db.rawQuery(query, null);

//        SELECT * FROM clientes INNER JOIN cidades ON clientes.cidade_id = cidades.id;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Receita receita = new Receita();
//                Toast.makeText(context, "FKIDUsuario: " +
//                        cursor.getLong(cursor.getColumnIndex(receita.FK.toString())), Toast.LENGTH_SHORT).show();

//                if (cursor.getLong(cursor.getColumnIndex(receita.FK.toString())) == idUsuario) {

                receita.setIdReceita(cursor.getLong(cursor.getColumnIndex(receita.ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(receita.NOME)));
                receita.setValorTransacao(new BigDecimal(cursor.getString(cursor.getColumnIndex(receita.VALOR_TRANSACAO))));
                receita.setMoeda(cursor.getString(cursor.getColumnIndex(receita.MOEDA)));
                receita.setFormaRecebimento(cursor.getString(cursor.getColumnIndex(receita.FORMA_RECEBIMENTO)));
                receita.setDate(new Date(cursor.getLong(cursor.getColumnIndex(receita.DATA))));

                adpReceitas.add(receita);

//                }

            } while (cursor.moveToNext());
        }

        return adpReceitas;

    }

    public DespesaArrayAdapter buscaDespesasPorIDUsuario(Context context, Long idUsuario) {

        DespesaArrayAdapter adpDespesas = new DespesaArrayAdapter(context, R.layout.act_item_despesas);

        String query = "select * from despesas where FKIDUsuario = " + idUsuario;
        Cursor cursor = db.rawQuery(query, null);

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

    public boolean buscarEmailExistente(String email) {

        db = this.getReadableDatabase();
        String query = "select email from usuarios where email = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) { // então encontrou o email na base
            return true;
        }

        return false; // não encontrou o email na base
    }

    public String buscarSenha(String email) {

        String senha = null;

        db = this.getReadableDatabase();
        String query = "select senha from usuarios where email = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                senha = cursor.getString(0).toString();

                break;
            } while (cursor.moveToNext());
        }

        return senha;
    }

    public Float somarReceita(Long idUsuario) {

        Float total = 0f;

        db = this.getReadableDatabase();
        String query = "select sum(valorTransacao) from receitas where FKIDUsuario = " + idUsuario;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                total = cursor.getFloat(0);

                break;
            } while (cursor.moveToNext());
        }

        return total;
    }

    public Float somarDespesa(Long idUsuario) {

        Float total = 0f;

        db = this.getReadableDatabase();
        String query = "select sum(valorTransacao) from despesas where FKIDUsuario = " + idUsuario;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                total = cursor.getFloat(0);

                break;
            } while (cursor.moveToNext());
        }

        return total;
    }

    public ArrayList<String> buscarPerfilPorEmail(String email) {

        ArrayList<String> dados = new ArrayList<>();

        db = this.getReadableDatabase();
        String query = "select * from usuarios where email = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                dados.add(cursor.getString(0));
                dados.add(cursor.getString(1));
                dados.add(cursor.getString(2));
                dados.add(cursor.getString(3));

            } while (cursor.moveToNext());
        }

        return dados;
    }

}//fim da classe
