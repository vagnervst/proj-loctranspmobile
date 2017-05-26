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
    private double combustivelRestante;
    private double tanqueVeiculo;
    private String veiculo;
    private String dataRetirada;
    private String dataEntrega;
    private String dataEntregaEfetuada;
    private int idStatusPedido;
    private String statusPedido;
    private String nomeLocador;
    private String nomeLocatario;
    private String imagemPrincipal;
    private int limiteQuilometragem;
    private int numeroCnh;
    private int quilometragemExcedida;
    private Integer localRetiradaLocatario;
    private Integer localDevolucaoLocatario;
    private Integer localRetiradaLocador;
    private Integer localDevolucaoLocador;
    private Integer solicitacaoRetiradaLocatario;
    private Integer solicitacaoDevolucaoLocatario;
    private Integer solicitacaoRetiradaLocador;
    private Integer solicitacaoDevolucaoLocador;
    private int idUsuarioLocatario;
    private int idUsuarioLocador;
    private int pagamentoPendenciaLocador;
    private int pagamentoPendenciaLocatario;
    private int idPublicacao;
    private int idTipoPedido;
    private int idFormaPagamento;
    private int idFormaPagamentoPendencias;
    private int idFuncionario;
    private int idCnh;
    private int idVeiculo;
    private int locatarioAvaliado;
    private int locadorAvaliado;
    private double valorVeiculo;
    private int idCategoriaVeiculo;
    private int diarias;
    private String sobrenomeLocador;
    private String locadorCelular;
    private String locadorTelefone;
    private String cidadeLocador;
    private String estadoLocador;
    private String locatarioCelular;
    private String locatarioEmail;
    private String locatarioTelefone;
    private String sobrenomeLocatario;
    private String cidadeLocatario;
    private String estadoLocatario;
    private String imagemPerfilLocatario;
    private HistoricoAlteracaoPedido[] historicoAlteracao;

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

    public String getDataEntregaEfetuada() {
        return dataEntregaEfetuada;
    }

    public void setDataEntregaEfetuada(String dataEntregaEfetuada) {
        this.dataEntregaEfetuada = dataEntregaEfetuada;
    }

    public double getCombustivelRestante() {
        return combustivelRestante;
    }

    public void setCombustivelRestante(double combustivelRestante) {
        this.combustivelRestante = combustivelRestante;
    }

    public int getQuilometragemExcedida() {
        return quilometragemExcedida;
    }

    public void setQuilometragemExcedida(int quilometragemExcedida) {
        this.quilometragemExcedida = quilometragemExcedida;
    }

    public HistoricoAlteracaoPedido[] getHistoricoAlteracao() {
        return historicoAlteracao;
    }

    public void setHistoricoAlteracao(HistoricoAlteracaoPedido[] historicoAlteracao) {
        this.historicoAlteracao = historicoAlteracao;
    }

    public double getTanqueVeiculo() {
        return tanqueVeiculo;
    }

    public void setTanqueVeiculo(double tanqueVeiculo) {
        this.tanqueVeiculo = tanqueVeiculo;
    }

    public int getPagamentoPendenciaLocador() {
        return pagamentoPendenciaLocador;
    }

    public void setPagamentoPendenciaLocador(int pagamentoPendenciaLocador) {
        this.pagamentoPendenciaLocador = pagamentoPendenciaLocador;
    }

    public int getPagamentoPendenciaLocatario() {
        return pagamentoPendenciaLocatario;
    }

    public void setPagamentoPendenciaLocatario(int pagamentoPendenciaLocatario) {
        this.pagamentoPendenciaLocatario = pagamentoPendenciaLocatario;
    }

    public int getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(int idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public int getIdTipoPedido() {
        return idTipoPedido;
    }

    public void setIdTipoPedido(int idTipoPedido) {
        this.idTipoPedido = idTipoPedido;
    }

    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public int getIdFormaPagamentoPendencias() {
        return idFormaPagamentoPendencias;
    }

    public void setIdFormaPagamentoPendencias(int idFormaPagamentoPendencias) {
        this.idFormaPagamentoPendencias = idFormaPagamentoPendencias;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdCnh() {
        return idCnh;
    }

    public void setIdCnh(int idCnh) {
        this.idCnh = idCnh;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public int getLocatarioAvaliado() {
        return locatarioAvaliado;
    }

    public void setLocatarioAvaliado(int locatarioAvaliado) {
        this.locatarioAvaliado = locatarioAvaliado;
    }

    public int getLocadorAvaliado() {
        return locadorAvaliado;
    }

    public void setLocadorAvaliado(int locadorAvaliado) {
        this.locadorAvaliado = locadorAvaliado;
    }

    public double getValorVeiculo() {
        return valorVeiculo;
    }

    public void setValorVeiculo(double valorVeiculo) {
        this.valorVeiculo = valorVeiculo;
    }

    public int getIdCategoriaVeiculo() {
        return idCategoriaVeiculo;
    }

    public void setIdCategoriaVeiculo(int idCategoriaVeiculo) {
        this.idCategoriaVeiculo = idCategoriaVeiculo;
    }

    public int getDiarias() {
        return diarias;
    }

    public void setDiarias(int diarias) {
        this.diarias = diarias;
    }

    public String getSobrenomeLocador() {
        return sobrenomeLocador;
    }

    public void setSobrenomeLocador(String sobrenomeLocador) {
        this.sobrenomeLocador = sobrenomeLocador;
    }

    public String getLocadorCelular() {
        return locadorCelular;
    }

    public void setLocadorCelular(String locadorCelular) {
        this.locadorCelular = locadorCelular;
    }

    public String getLocadorTelefone() {
        return locadorTelefone;
    }

    public void setLocadorTelefone(String locadorTelefone) {
        this.locadorTelefone = locadorTelefone;
    }

    public String getCidadeLocador() {
        return cidadeLocador;
    }

    public void setCidadeLocador(String cidadeLocador) {
        this.cidadeLocador = cidadeLocador;
    }

    public String getEstadoLocador() {
        return estadoLocador;
    }

    public void setEstadoLocador(String estadoLocador) {
        this.estadoLocador = estadoLocador;
    }

    public String getLocatarioCelular() {
        return locatarioCelular;
    }

    public void setLocatarioCelular(String locatarioCelular) {
        this.locatarioCelular = locatarioCelular;
    }

    public String getLocatarioEmail() {
        return locatarioEmail;
    }

    public void setLocatarioEmail(String locatarioEmail) {
        this.locatarioEmail = locatarioEmail;
    }

    public String getLocatarioTelefone() {
        return locatarioTelefone;
    }

    public void setLocatarioTelefone(String locatarioTelefone) {
        this.locatarioTelefone = locatarioTelefone;
    }

    public String getSobrenomeLocatario() {
        return sobrenomeLocatario;
    }

    public void setSobrenomeLocatario(String sobrenomeLocatario) {
        this.sobrenomeLocatario = sobrenomeLocatario;
    }

    public String getCidadeLocatario() {
        return cidadeLocatario;
    }

    public void setCidadeLocatario(String cidadeLocatario) {
        this.cidadeLocatario = cidadeLocatario;
    }

    public String getEstadoLocatario() {
        return estadoLocatario;
    }

    public void setEstadoLocatario(String estadoLocatario) {
        this.estadoLocatario = estadoLocatario;
    }

    public String getImagemPerfilLocatario() {
        return imagemPerfilLocatario;
    }

    public void setImagemPerfilLocatario(String imagemPerfilLocatario) {
        this.imagemPerfilLocatario = imagemPerfilLocatario;
    }
}