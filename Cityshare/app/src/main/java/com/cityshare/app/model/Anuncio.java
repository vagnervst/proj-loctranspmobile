package com.cityshare.app.model;

/**
 * Created by vagne_000 on 07/05/2017.
 */

public class Anuncio {
    private int id;
    private int quilometragemAtual;
    private int limiteQuilometragem;
    private int qtdPortas;
    private String titulo;
    private String descricao;
    private String localizacao;
    private String imagemPrincipal;
    private String imagemA;
    private String imagemB;
    private String imagemC;
    private String imagemD;
    private String combustivel;
    private String cidade;
    private String estado;
    private double valorDiaria;
    private double valorCombustivel;
    private double valorQuilometragem;
    private double avaliacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(int quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }

    public int getQtdPortas() {
        return qtdPortas;
    }

    public void setQtdPortas(int qtdPortas) {
        this.qtdPortas = qtdPortas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public double getValorCombustivel() {
        return valorCombustivel;
    }

    public void setValorCombustivel(double valorCombustivel) {
        this.valorCombustivel = valorCombustivel;
    }

    public double getValorQuilometragem() {
        return valorQuilometragem;
    }

    public void setValorQuilometragem(double valorQuilometragem) {
        this.valorQuilometragem = valorQuilometragem;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getLimiteQuilometragem() {
        return limiteQuilometragem;
    }

    public void setLimiteQuilometragem(int limiteQuilometragem) {
        this.limiteQuilometragem = limiteQuilometragem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemA() {
        return imagemA;
    }

    public void setImagemA(String imagemA) {
        this.imagemA = imagemA;
    }

    public String getImagemB() {
        return imagemB;
    }

    public void setImagemB(String imagemB) {
        this.imagemB = imagemB;
    }

    public String getImagemC() {
        return imagemC;
    }

    public void setImagemC(String imagemC) {
        this.imagemC = imagemC;
    }

    public String getImagemD() {
        return imagemD;
    }

    public void setImagemD(String imagemD) {
        this.imagemD = imagemD;
    }
}
