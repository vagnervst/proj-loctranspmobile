package com.cityshare.app.model;

/**
 * Created by vagne_000 on 17/05/2017.
 */

public class Cnh {
    private int id;
    private int numeroRegistro;
    private int idUsuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return String.valueOf(this.numeroRegistro);
    }
}
