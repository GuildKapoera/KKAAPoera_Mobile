package com.guild.kaapoera.Activity;

public class QueroPTGeral {
    //Variaveis do firestore QueroPT
    String data;

    private String PTid;
    private String EKnome;
    private String EKcod;
    private String EKcontato;
    private int EKlevel;
    private String EDnome;
    private String EDcod;
    private String EDcontato;
    private int EDlevel;
    private String RPnome;
    private String RPcod;
    private String RPcontato;
    private int RPlevel;
    private String MSnome;
    private String MScod;
    private String MScontato;
    private int MSlevel;

    private String Contato;
    private String Horario;
    private String Responsavel;

    private int LevelMaximo;
    private int LevelMinimo;

    //Contrutor vazio Firestore
    public QueroPTGeral(){
    }
    //Getter e Setters
    public String getContato(){ return Contato; }
    public void setContato(String Contato){this.Contato = Contato;}
    public String getHorario(){ return Horario; }
    public void setHorario(String Horario){this.Horario = Horario;}
    public String getResponsavel(){ return Responsavel; }
    public void setResponsavel(String Responsavel){this.Responsavel = Responsavel;}
    public int getLevelMaximo(){ return LevelMaximo; }
    public void setLevelMaximo(int LevelMaximo){this.LevelMaximo = LevelMaximo;}
    public int getLevelMinimo(){ return LevelMinimo; }
    public void setLevelMinimo(int LevelMinimo){this.LevelMinimo = LevelMinimo;}
    public String getEKnome(){ return EKnome; }
    public void setEKnome(String EKnome){this.EKnome = EKnome;}
    public String getEKcontato(){ return EKcontato;}
    public void setEKcontato(String EKcontato){this.EKcontato = EKcontato;}
    public String getEKcod(){return EKcod;}
    public void setEKcod(String EKcod){this.EKcod = EKcod;}
    public int getEKlevel(){ return EKlevel;}
    public void setEKlevel(int EKlevel){this.EKlevel = EKlevel;}
    public String getEDnome(){ return EDnome; }
    public void setEDnome(String EDnome){this.EDnome = EDnome;}
    public String getEDcontato(){ return EDcontato; }
    public void setEDcontato(String EDcontato){this.EDcontato = EDcontato;}
    public String getEDcod() {return EDcod;}
    public void setEDcod(String EDcod){this.EDcod = EDcod;}
    public int getEDlevel(){ return EDlevel;}
    public void setEDlevel(int EDlevel){this.EDlevel = EDlevel;}
    public String getRPnome(){ return RPnome;}
    public void setRPnome(String RPnome){this.RPnome = RPnome;}
    public String getRPcontato(){ return RPcontato;}
    public void setRPcontato(String RPcontato){this.RPcontato = RPcontato;}
    public String getRPcod(){return RPcod;}
    public void setRPcod(String RPcod){this.RPcod = RPcod;}
    public int getRPlevel(){ return RPlevel;}
    public void setRPlevel(int RPlevel){this.RPlevel = RPlevel;}
    public String getMSnome(){ return MSnome;}
    public void setMSnome(String MSnome){this.MSnome = MSnome;}
    public String getMScontato(){ return MScontato;}
    public void setMScontato(String MScontato){this.MScontato = MScontato;}
    public String getMScod(){return MScod;}
    public void setMScod(String MScod){this.MScod = MScod;}
    public int getMSlevel(){ return MSlevel;}
    public void setMSlevel(int MSlevel){this.MSlevel = MSlevel;}
    public String getPTid() { return PTid; }
    public void setPTid(String PTid) {this.PTid = PTid;}
    public String getData() {return data;}
    public void setData(String data){this.data = data;}

    public boolean usuarioEstaNaPT(String nomePersonagem) {
        if (getEKnome().equals(nomePersonagem) || getRPnome().equals(nomePersonagem) || getEDnome().equals(nomePersonagem) || getMSnome().equals(nomePersonagem)) {
            return true;
        }
        return false;
    }
}