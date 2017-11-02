package com.example.thalesdasilva.countapp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ThalesdaSilva on 17/07/2017.
 */

public class Despesa implements Serializable {

    //Constantes Públicas
    public static String TABELA = "despesas";
    public static String ID = "IDDespesa";
    public static String NOME = "nome";
    public static String VALOR_TRANSACAO = "valorTransacao";
    public static String MOEDA = "moeda";
    public static String FORMA_PAGAMENTO = "formaPagamento";
    public static String DATA = "data";
    public static String FK = "FKIDUsuario";

    //Atributos
    private Long IDDespesa;
    private String nome;
    private BigDecimal valorTransacao;
    private String moeda;
    private String formaPagamento;
    private Date data;
    private Long FKIDUsuario;

    //Método Construtor
    public Despesa() {
        IDDespesa = 0L;
    }

    //Métodos Assessores
    public Long getIDDespesa() {
        return IDDespesa;
    }

    public void setIDDespesa(Long idReceita) {
        this.IDDespesa = idReceita;
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

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
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