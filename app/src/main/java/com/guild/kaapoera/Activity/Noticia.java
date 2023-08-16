package com.guild.kaapoera.Activity;

public class Noticia {
    private String titulo;
    private String conteudo;
    private String autor;
    private String data;

    // Construtor vazio necessário para o Firebase Firestore
    public Noticia() {
        // Construtor vazio
    }

    public Noticia(String titulo, String conteudo, String autor, String data) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.autor = autor;
        this.data = data;
    }

    // Getters e Setters para os campos
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
