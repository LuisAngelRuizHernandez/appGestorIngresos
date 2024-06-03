package com.example.pruebacanciones.entidades;

import java.util.Date;

public class Gasto {
    private int id; // pk
    private String nombreGasto;
    private String fechaGasto;  // Fecha como String
    private int valorGasto;
    private String descripcionGasto;
    private int _IDusuario; // fk

    public Gasto(int id, String nombreGasto, String fechaGasto, int valorGasto, String descripcionGasto, int _IDusuario) {
        this.id = id;
        this.nombreGasto = nombreGasto;
        this.fechaGasto = fechaGasto;
        this.valorGasto = valorGasto;
        this.descripcionGasto = descripcionGasto;
        this._IDusuario = _IDusuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreGasto() {
        return nombreGasto;
    }

    public void setNombreGasto(String nombreGasto) {
        this.nombreGasto = nombreGasto;
    }

    public String getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(String fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

    public int getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(int valorGasto) {
        this.valorGasto = valorGasto;
    }

    public String getDescripcionGasto() {
        return descripcionGasto;
    }

    public void setDescripcionGasto(String descripcionGasto) {
        this.descripcionGasto = descripcionGasto;
    }

    public int get_IDusuario() {
        return _IDusuario;
    }

    public void set_IDusuario(int _IDusuario) {
        this._IDusuario = _IDusuario;
    }
}
