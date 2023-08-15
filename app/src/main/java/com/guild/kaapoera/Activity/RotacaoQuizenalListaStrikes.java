package com.guild.kaapoera.Activity;

public class RotacaoQuizenalListaStrikes {

    private String nomePersonagem;
    private String strike;
    private String uid;

    public RotacaoQuizenalListaStrikes(){

    }

    public RotacaoQuizenalListaStrikes(String nomePersonagem, String strike){
        this.strike = strike;
        this.nomePersonagem = nomePersonagem;

    }

    //Getter and Setters


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    public String getStrike() {
        return strike;
    }

    public void setStrike(String strike) {
        this.strike = strike;
    }
}
