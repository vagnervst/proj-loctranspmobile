package com.cityshare.app.model;

/**
 * Created by Leo on 5/20/2017.
 */

public class Veiculo {
    private int id;
    private String nome;
    private String tipoMotor;
    private String codigo;
    private double tanque;
    private int ano;
    private int qtdPortas;
    private int idCategoriaVeiculo;
    private int idFabricante;
    private int idTipoCombustivel;
    private int idTipoVeiculo;
    private int idTransmissao;
    private int visivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getTanque() {
        return tanque;
    }

    public void setTanque(double tanque) {
        this.tanque = tanque;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getQtdPortas() {
        return qtdPortas;
    }

    public void setQtdPortas(int qtdPortas) {
        this.qtdPortas = qtdPortas;
    }

    public int getIdCategoriaVeiculo() {
        return idCategoriaVeiculo;
    }

    public void setIdCategoriaVeiculo(int idCategoriaVeiculo) {
        this.idCategoriaVeiculo = idCategoriaVeiculo;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public int getIdTipoCombustivel() {
        return idTipoCombustivel;
    }

    public void setIdTipoCombustivel(int idTipoCombustivel) {
        this.idTipoCombustivel = idTipoCombustivel;
    }

    public int getIdTipoVeiculo() {
        return idTipoVeiculo;
    }

    public void setIdTipoVeiculo(int idTipoVeiculo) {
        this.idTipoVeiculo = idTipoVeiculo;
    }

    public int getIdTransmissao() {
        return idTransmissao;
    }

    public void setIdTransmissao(int idTransmissao) {
        this.idTransmissao = idTransmissao;
    }

    public int getVisivel() {
        return visivel;
    }

    public void setVisivel(int visivel) {
        this.visivel = visivel;
    }
}
