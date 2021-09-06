package com.example.poolcorunha;

import java.io.Serializable;

public class Usuario implements Serializable {
    String email, login, password, fecha, genero;
    int id;

    public Usuario(String email, String password, String login, int id, String fecha, String genero) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.id = id;
        this.fecha = fecha;
        this.genero = genero;
    }
    public Usuario() {
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String nombre) {
        this.email = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
