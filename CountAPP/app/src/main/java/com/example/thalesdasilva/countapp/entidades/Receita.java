package com.example.thalesdasilva.countapp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ThalesdaSilva on 17/07/2017.
 */

public class Receita implements Serializable {

    //Constantes Públicas
    public static String TABELA = "receitas";
    public static String ID = "IDReceita";
    public static String NOME = "nome";
    public static String VALOR_TRANSACAO = "valorTransacao";
    public static String MOEDA = "moeda";
    public static String FORMA_RECEBIMENTO = "formaRecebimento";
    public static String DATA = "data";
    public static String FK = "FKIDUsuario";

    //Atributos
    private Long IDReceita;
    private String nome;
    private BigDecimal valorTransacao;
    private String moeda;
    private String formaRecebimento;
    private Date data;
    private Long FKIDUsuario;

    //Método Construtor
    public Receita() {
        IDReceita = 0L;
    }

    //Métodos Assessores
    public Long getIdReceita() {
        return IDReceita;
    }

    public void setIdReceita(Long idReceita) {
        this.IDReceita = idReceita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorTransacao() {
        return valorTransacao;
    }

    public void setValorTransacao(BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getFormaRecebimento() {
        return formaRecebimento;
    }

    public void setFormaRecebimento(String formaRecebimento) {
        this.formaRecebimento = formaRecebimento;
    }

    public Date getDate() {
        return data;
    }

    public void setDate(Date data) {
        this.data = data;
    }

    public Long getFKIDUsuario() {
        return FKIDUsuario;
    }

    public void setFKIDUsuario(Long FKIDUsuario) {
        this.FKIDUsuario = FKIDUsuario;
    }

    @Override
    public String toString() {
        return getNome() + " " + getValorTransacao();
    }

}//fim da classe