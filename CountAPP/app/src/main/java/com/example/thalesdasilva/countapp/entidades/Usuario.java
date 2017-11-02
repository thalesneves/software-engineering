package com.example.thalesdasilva.countapp.entidades;

import java.io.Serializable;

/**
 * Created by ThalesdaSilva on 14/10/2017.
 */

public class Usuario implements Serializable{

    //Constantes Públicas
    public static String TABELA = "usuarios";
    public static String ID = "IDUsuario";
    public static String EMAIL = "email";
    public static String NOME_USUARIO = "nomeUsuario";
    public static String SENHA = "senha";

    //Atributos
    private Long IDUsuario;
    private String email;
    private String nomeUsuario;
    private String senha;

    //Método Construtor
    public Usuario() {
        IDUsuario = 0L;
    }

    //Métodos Acessores
    public Long getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(Long IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}//fim da classe
