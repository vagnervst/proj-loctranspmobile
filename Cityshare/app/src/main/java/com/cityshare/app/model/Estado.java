package com.cityshare.app.model;

/**
 * Created by vagne_000 on 22/05/2017.
 */

public class Estado {
    private int id;
    private String nome;
    private int idPais;

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

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
