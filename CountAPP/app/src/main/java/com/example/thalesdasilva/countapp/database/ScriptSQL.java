package com.example.thalesdasilva.countapp.database;

/**
 * Created by ThalesdaSilva on 11/07/2017.
 */

public class ScriptSQL {

    public static String getCreateUsuarios() {

        //Classse StringBuilder para concatenar strings grandes
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table if not exists usuarios ( ");
        sqlBuilder.append("IDUsuario         integer   not null ");
        sqlBuilder.append("primary key autoincrement, ");
        sqlBuilder.append("email            VARCHAR (45), ");
        sqlBuilder.append("nomeUsuario      VARCHAR (45), ");
        sqlBuilder.append("senha            VARCHAR (20) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

    public static String getCreateContas() {

        //Classse StringBuilder para concatenar strings grandes
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table if not exists contas ( ");
        sqlBuilder.append("IDConta         integer   not null ");
        sqlBuilder.append("primary key autoincrement, ");
        sqlBuilder.append("FKIDReceita INTEGER REFERENCES receitas (IDReceita), ");
        sqlBuilder.append("FKIDDespesa INTEGER REFERENCES despesas (IDDespesa) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

    public static String getCreateFormasPagamento() {

        //Classse StringBuilder para concatenar strings grandes
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table if not exists formaspagamento ( ");
        sqlBuilder.append("IDFormasPagamento         integer   not null ");
        sqlBuilder.append("primary key autoincrement, ");
        sqlBuilder.append("nomeFormasPagamento     varchar(45) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

    public static String getCreateReceitas() {

        //Classse StringBuilder para concatenar strings grandes
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table if not exists receitas ( ");
        sqlBuilder.append("IDReceita         integer   not null ");
        sqlBuilder.append("primary key autoincrement, ");
        sqlBuilder.append("nome                     VARCHAR (45), ");
        sqlBuilder.append("valorTransacao           NUMERIC, ");
        sqlBuilder.append("moeda                    VARCHAR (45), ");
        sqlBuilder.append("formaRecebimento         VARCHAR (45), ");
        sqlBuilder.append("data                     DATE, ");
        sqlBuilder.append("FKIDUsuario              INTEGER REFERENCES usuarios (IDUsuario), ");
        sqlBuilder.append("FKIDFormaRecebimento     INTEGER REFERENCES formaspagamento (IDFormaRecebimento),");
        sqlBuilder.append("FKIDConta                INTEGER REFERENCES contas (IDConta) " );
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

    public static String getCreateDespesas() {

        //Classse StringBuilder para concatenar strings grandes
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table if not exists despesas ( ");
        sqlBuilder.append("IDDespesa         integer   not null ");
        sqlBuilder.append("primary key autoincrement, ");
        sqlBuilder.append("nome                 VARCHAR (45), ");
        sqlBuilder.append("valorTransacao       NUMERIC, ");
        sqlBuilder.append("moeda                VARCHAR (45), ");
        sqlBuilder.append("formaPagamento       VARCHAR (45), ");
        sqlBuilder.append("data                 DATE, ");
        sqlBuilder.append("FKIDUsuario          INTEGER REFERENCES usuarios (IDUsuario), " );
        sqlBuilder.append("FKIDFormaPagamento   INTEGER REFERENCES formaspagamento (IDFormaRecebimento), " );
        sqlBuilder.append("FKIDConta            INTEGER REFERENCES contas (IDConta) " );
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

    public static String getCreateSaldo() {

        //Classse StringBuilder para concatenar strings grandes
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("create table if not exists saldo ( ");
        sqlBuilder.append("IDSaldo         integer   not null ");
        sqlBuilder.append("primary key autoincrement, ");
        sqlBuilder.append("saldo                    numeric, ");
        sqlBuilder.append("moeda                    varchar(45), ");
        sqlBuilder.append("valorConversao           numeric, ");
        sqlBuilder.append("nomeConta                varchar(45), ");
        sqlBuilder.append("dataTransferencia        date, ");
        sqlBuilder.append("dataAtualizacao          date, ");
        sqlBuilder.append("FKIDReceitasDespesas     integer references receitasdespesas (IDReceitaDespesa), " );
        sqlBuilder.append("FKIDFormaPagamento      integer references formaspagamento (IDFormaPagamento), " );
        sqlBuilder.append("FKIDConta                integer references contas (IDConta), " );
        sqlBuilder.append("FKIDUsuario              integer references usuarios (IDUsuario) " );
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

}//fim da classe
