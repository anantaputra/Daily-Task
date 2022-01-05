package com.example.dailytask.model;

public class User {
    String username, jenisKelamin, Tgllahir, email, foto;

    public User(String username, String jenisKelamin, String tgllahir, String email, String foto) {
        this.username = username;
        this.jenisKelamin = jenisKelamin;
        this.Tgllahir = tgllahir;
        this.email = email;
        this.foto = foto;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTgllahir() {
        return Tgllahir;
    }

    public void setTgllahir(String tgllahir) {
        Tgllahir = tgllahir;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
