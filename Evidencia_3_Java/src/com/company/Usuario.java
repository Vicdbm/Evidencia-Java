package com.company;

//clase base para paciente, doctor y administrador, no se instancia
//solo tiene atributos id y nombre, métodos unicamente getters y setters
public class Usuario {
    private int id;
    private String nombre;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
