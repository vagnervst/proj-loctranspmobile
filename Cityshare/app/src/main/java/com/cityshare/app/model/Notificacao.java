package com.cityshare.app.model;

/**
 * Created by vagne_000 on 24/05/2017.
 */

public class Notificacao {
    public static final int SOLICITACAO = 1, ATUALIZACAO_PEDIDO = 2, AVALIACAO = 3;
    private int id;
    private String mensagem;
    private int idUsuarioRementente;
    private String nomeRemetente;
    private String sobrenomeRemetente;
    private int idUsuarioDestinatario;
    private String nomeDestinatario;
    private String sobrenomeDestinatario;
    private int idPedido;
    private int idTipoNotificacao;
    private int visualizada;
    private String tipoNotificacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getIdUsuarioRementente() {
        return idUsuarioRementente;
    }

    public void setIdUsuarioRementente(int idUsuarioRementente) {
        this.idUsuarioRementente = idUsuarioRementente;
    }

    public int getIdUsuarioDestinatario() {
        return idUsuarioDestinatario;
    }

    public void setIdUsuarioDestinatario(int idUsuarioDestinatario) {
        this.idUsuarioDestinatario = idUsuarioDestinatario;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdTipoNotificacao() {
        return idTipoNotificacao;
    }

    public void setIdTipoNotificacao(int idTipoNotificacao) {
        this.idTipoNotificacao = idTipoNotificacao;
    }

    public int getVisualizada() {
        return visualizada;
    }

    public void setVisualizada(int visualizada) {
        this.visualizada = visualizada;
    }

    public String getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(String tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public String getSobrenomeRemetente() {
        return sobrenomeRemetente;
    }

    public void setSobrenomeRemetente(String sobrenomeRemetente) {
        this.sobrenomeRemetente = sobrenomeRemetente;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getSobrenomeDestinatario() {
        return sobrenomeDestinatario;
    }

    public void setSobrenomeDestinatario(String sobrenomeDestinatario) {
        this.sobrenomeDestinatario = sobrenomeDestinatario;
    }
}
