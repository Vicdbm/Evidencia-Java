package com.company;

//añade el atributo especialidad respecto a usuario
public class Doctor extends  Usuario {
    private String especialidad;

    public Doctor(int id, String nombre, String especialidad) {
        setId(id);
        setNombre(nombre);
        this.especialidad = especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}
