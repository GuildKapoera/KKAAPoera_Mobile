package com.guild.kaapoera.Activity;

public class AnuncioGerenciar {
    private String descricao;
    private String imagemUrl;
    private String nomePersonagem;
    private String preco;
    private String tipoAnuncio;
    private String titulo;
    private String uid; // Novo atributo para armazenar o UID do anúncio

    public AnuncioGerenciar() {
        // Construtor vazio necessário para o Firestore
    }

    public AnuncioGerenciar(String descricao, String imagemUrl, String nomePersonagem, String preco, String tipoAnuncio, String titulo) {
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.nomePersonagem = nomePersonagem;
        this.preco = preco;
        this.tipoAnuncio = tipoAnuncio;
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getTipoAnuncio() {
        return tipoAnuncio;
    }

    public void setTipoAnuncio(String tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
