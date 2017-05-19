package com.cityshare.app.model;

/**
 * Created by vagne_000 on 18/05/2017.
 */

public class HistoricoAlteracaoPedido {
    private int id;
    private int idStatusPedido;
    private String dataOcorrencia;
    private String tituloStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public int getIdStatusPedido() {
        return idStatusPedido;
    }

    public void setIdStatusPedido(int idStatusPedido) {
        this.idStatusPedido = idStatusPedido;
    }

    public String getTituloStatus() {
        return tituloStatus;
    }

    public void setTituloStatus(String tituloStatus) {
        this.tituloStatus = tituloStatus;
    }
}
