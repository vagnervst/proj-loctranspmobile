package com.cityshare.app.model;

/**
 * Created by vagne_000 on 08/05/2017.
 */

public class Usuario {
    private int id;
    private int idCidade;
    private int idTipoConta;
    private int idPlanoConta;
    private int idLicencaDesktop;
    private int autenticacaoDupla;
    private String nome;
    private String sobrenome;
    private char sexo;
    private String cpf;
    private String telefone;
    private String celular;
    private String email;
    private String rg;
    private String senha;
    private double saldo;
    private String dataNascimento;
    private String fotoPerfil;
    private Cnh[] listaCnh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public int getIdTipoConta() {
        return idTipoConta;
    }

    public void setIdTipoConta(int idTipoConta) {
        this.idTipoConta = idTipoConta;
    }

    public int getIdPlanoConta() {
        return idPlanoConta;
    }

    public void setIdPlanoConta(int idPlanoConta) {
        this.idPlanoConta = idPlanoConta;
    }

    public int getIdLicencaDesktop() {
        return idLicencaDesktop;
    }

    public void setIdLicencaDesktop(int idLicencaDesktop) {
        this.idLicencaDesktop = idLicencaDesktop;
    }

    public int getAutenticacaoDupla() {
        return autenticacaoDupla;
    }

    public void setAutenticacaoDupla(int autenticacaoDupla) {
        this.autenticacaoDupla = autenticacaoDupla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Cnh[] getListaCnh() {
        return listaCnh;
    }

    public void setListaCnh(Cnh[] listaCnh) {
        this.listaCnh = listaCnh;
    }
}
