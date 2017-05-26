package com.cityshare.app.model;

/**
 * Created by Leo on 5/20/2017.
 */

public class Transmissao {
    private int id;
    private String titulo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
