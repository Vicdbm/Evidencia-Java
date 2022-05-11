package com.company;

//añade el atributo password respecto a usuario
public class Administrador extends Usuario {
    private String password;

    public Administrador(int id, String nombre, String password) {
        setId(id);
        setNombre(nombre);
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
