package com.cityshare.app.model;

/**
 * Created by vagne_000 on 03/06/2017.
 */

public class Avaliacao {
    private int id;
    private int nota;
    private String mensagem;
    private int idUsuarioAvaliador;
    private int idUsuarioAvaliado;
    private String dataAvaliacao;
    private String nomeAvaliado;
    private String sobrenomeAvaliado;
    private String nomeAvaliador;
    private String sobrenomeAvaliador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getIdUsuarioAvaliador() {
        return idUsuarioAvaliador;
    }

    public void setIdUsuarioAvaliador(int idUsuarioAvaliador) {
        this.idUsuarioAvaliador = idUsuarioAvaliador;
    }

    public int getIdUsuarioAvaliado() {
        return idUsuarioAvaliado;
    }

    public void setIdUsuarioAvaliado(int idUsuarioAvaliado) {
        this.idUsuarioAvaliado = idUsuarioAvaliado;
    }

    public String getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(String dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getNomeAvaliado() {
        return nomeAvaliado;
    }

    public void setNomeAvaliado(String nomeAvaliado) {
        this.nomeAvaliado = nomeAvaliado;
    }

    public String getSobrenomeAvaliado() {
        return sobrenomeAvaliado;
    }

    public void setSobrenomeAvaliado(String sobrenomeAvaliado) {
        this.sobrenomeAvaliado = sobrenomeAvaliado;
    }

    public String getNomeAvaliador() {
        return nomeAvaliador;
    }

    public void setNomeAvaliador(String nomeAvaliador) {
        this.nomeAvaliador = nomeAvaliador;
    }

    public String getSobrenomeAvaliador() {
        return sobrenomeAvaliador;
    }

    public void setSobrenomeAvaliador(String sobrenomeAvaliador) {
        this.sobrenomeAvaliador = sobrenomeAvaliador;
    }
}
