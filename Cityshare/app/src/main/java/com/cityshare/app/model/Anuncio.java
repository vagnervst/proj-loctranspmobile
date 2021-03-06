package com.cityshare.app.model;

/**
 * Created by vagne_000 on 07/05/2017.
 */

public class Anuncio {
    public static final int DISPONIVEL = 1, INDISPONIVEL = 2;

    private int id;
    private int quilometragemAtual;
    private int limiteQuilometragem;
    private int qtdPortas;
    private int idLocador;
    private int idStatusPublicacao;
    private String nomeLocador;
    private String sobrenomeLocador;
    private String fotoLocador;
    private String titulo;
    private String descricao;
    private String localizacaoUsuario;
    private String localizacaoAgencia;
    private String imagemPrincipal;
    private String imagemA;
    private String imagemB;
    private String imagemC;
    private String imagemD;
    private String combustivel;
    private String cidade;
    private String estado;
    private String modeloVeiculo;
    private String statusPedido;
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

    public String getLocalizacaoUsuario() {
        return localizacaoUsuario;
    }

    public void setLocalizacaoUsuario(String localizacaoUsuario) {
        this.localizacaoUsuario = localizacaoUsuario;
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

    public int getIdLocador() {
        return idLocador;
    }

    public void setIdLocador(int idLocador) {
        this.idLocador = idLocador;
    }

    public String getNomeLocador() {
        return nomeLocador;
    }

    public void setNomeLocador(String nomeLocador) {
        this.nomeLocador = nomeLocador;
    }

    public String getSobrenomeLocador() {
        return sobrenomeLocador;
    }

    public void setSobrenomeLocador(String sobrenomeLocador) {
        this.sobrenomeLocador = sobrenomeLocador;
    }

    public String getFotoLocador() {
        return fotoLocador;
    }

    public void setFotoLocador(String fotoLocador) {
        this.fotoLocador = fotoLocador;
    }

    public String getLocalizacaoAgencia() {
        return localizacaoAgencia;
    }

    public void setLocalizacaoAgencia(String localizacaoAgencia) {
        this.localizacaoAgencia = localizacaoAgencia;
    }

    public int getIdStatusPublicacao() {
        return idStatusPublicacao;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public void setIdStatusPublicacao(int idStatusPublicacao) {
        this.idStatusPublicacao = idStatusPublicacao;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }
}
