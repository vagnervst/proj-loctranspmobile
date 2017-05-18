package com.cityshare.app.model;

/**
 * Created by vagne_000 on 09/05/2017.
 */

public class Pedido {
    private int id;
    private double valorDiaria;
    private double valorCombustivel;
    private double valorQuilometragem;
    private double valorTotal;
    private String veiculo;
    private String dataRetirada;
    private String dataEntrega;
    private int idStatusPedido;
    private String statusPedido;
    private String nomeLocador;
    private String nomeLocatario;
    private String imagemPrincipal;
    private int limiteQuilometragem;
    private int numeroCnh;
    private int localRetiradaLocatario;
    private int localDevolucaoLocatario;
    private int localRetiradaLocador;
    private int localDevolucaoLocador;
    private int solicitacaoRetiradaLocatario;
    private int solicitacaoDevolucaoLocatario;
    private int solicitacaoRetiradaLocador;
    private int solicitacaoDevolucaoLocador;
    private int idUsuarioLocatario;
    private int idUsuarioLocador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getNomeLocador() {
        return nomeLocador;
    }

    public void setNomeLocador(String nomeLocador) {
        this.nomeLocador = nomeLocador;
    }

    public String getNomeLocatario() {
        return nomeLocatario;
    }

    public void setNomeLocatario(String nomeLocatario) {
        this.nomeLocatario = nomeLocatario;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
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

    public int getLimiteQuilometragem() {
        return limiteQuilometragem;
    }

    public void setLimiteQuilometragem(int limiteQuilometragem) {
        this.limiteQuilometragem = limiteQuilometragem;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getNumeroCnh() {
        return numeroCnh;
    }

    public void setNumeroCnh(int numeroCnh) {
        this.numeroCnh = numeroCnh;
    }

    public int getIdStatusPedido() {
        return idStatusPedido;
    }

    public void setIdStatusPedido(int idStatusPedido) {
        this.idStatusPedido = idStatusPedido;
    }


    public int getLocalRetiradaLocatario() {
        return localRetiradaLocatario;
    }

    public void setLocalRetiradaLocatario(int localRetiradaLocatario) {
        this.localRetiradaLocatario = localRetiradaLocatario;
    }

    public int getLocalDevolucaoLocatario() {
        return localDevolucaoLocatario;
    }

    public void setLocalDevolucaoLocatario(int localDevolucaoLocatario) {
        this.localDevolucaoLocatario = localDevolucaoLocatario;
    }

    public int getLocalRetiradaLocador() {
        return localRetiradaLocador;
    }

    public void setLocalRetiradaLocador(int localRetiradaLocador) {
        this.localRetiradaLocador = localRetiradaLocador;
    }

    public int getLocalDevolucaoLocador() {
        return localDevolucaoLocador;
    }

    public void setLocalDevolucaoLocador(int localDevolucaoLocador) {
        this.localDevolucaoLocador = localDevolucaoLocador;
    }

    public int getSolicitacaoRetiradaLocatario() {
        return solicitacaoRetiradaLocatario;
    }

    public void setSolicitacaoRetiradaLocatario(int solicitacaoRetiradaLocatario) {
        this.solicitacaoRetiradaLocatario = solicitacaoRetiradaLocatario;
    }

    public int getSolicitacaoDevolucaoLocatario() {
        return solicitacaoDevolucaoLocatario;
    }

    public void setSolicitacaoDevolucaoLocatario(int solicitacaoDevolucaoLocatario) {
        this.solicitacaoDevolucaoLocatario = solicitacaoDevolucaoLocatario;
    }

    public int getSolicitacaoRetiradaLocador() {
        return solicitacaoRetiradaLocador;
    }

    public void setSolicitacaoRetiradaLocador(int solicitacaoRetiradaLocador) {
        this.solicitacaoRetiradaLocador = solicitacaoRetiradaLocador;
    }

    public int getSolicitacaoDevolucaoLocador() {
        return solicitacaoDevolucaoLocador;
    }

    public void setSolicitacaoDevolucaoLocador(int solicitacaoDevolucaoLocador) {
        this.solicitacaoDevolucaoLocador = solicitacaoDevolucaoLocador;
    }

    public int getIdUsuarioLocatario() {
        return idUsuarioLocatario;
    }

    public void setIdUsuarioLocatario(int idUsuarioLocatario) {
        this.idUsuarioLocatario = idUsuarioLocatario;
    }

    public int getIdUsuarioLocador() {
        return idUsuarioLocador;
    }

    public void setIdUsuarioLocador(int idUsuarioLocador) {
        this.idUsuarioLocador = idUsuarioLocador;
    }

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }
}