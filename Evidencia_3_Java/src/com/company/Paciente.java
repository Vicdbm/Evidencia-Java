package com.company;

//no añade nada respecto a usuario, pero se crea para que al necesitarse un paciente no se puedan poner doctores o administradores
public class Paciente extends Usuario{

    Paciente(int id, String nombre) {
        setId(id);
        setNombre(nombre);
    }
}
