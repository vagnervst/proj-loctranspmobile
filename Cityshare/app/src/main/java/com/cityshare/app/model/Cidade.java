package com.cityshare.app.model;

/**
 * Created by vagne_000 on 22/05/2017.
 */

public class Cidade {
    private int id;
    private String nome;
    private int idEstado;

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

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
