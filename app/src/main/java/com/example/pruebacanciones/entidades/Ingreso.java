package com.example.pruebacanciones.entidades;

public class Ingreso {
 // entidad de la clase ingreso
    private int ID; // pk
    private int _IDusuario; // fk
    private String nombre;
    private int valor;
    private String descripcion;
    private String fuente;

    public Ingreso(int ID, int _IDusuario, String nombre, int valor, String descripcion, String fuente) {
        this.ID = ID;
        this._IDusuario = _IDusuario;
        this.nombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
        this.fuente = fuente;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int get_IDusuario() {
        return _IDusuario;
    }

    public void set_IDusuario(int _IDusuario) {
        this._IDusuario = _IDusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
}
