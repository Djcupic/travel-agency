package model;
// Generated Sep 2, 2017 3:19:43 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Hotel generated by hbm2java
 */
public class Hotel  implements java.io.Serializable {


     private Integer idHotela;
     private Destinacija destinacija;
     private String imeHotela;
     private int zvezdice;
     private String opisHotela;
     private Set<CenaSobe> cenaSobes = new HashSet<CenaSobe>(0);
     private Set<Slika> slikas = new HashSet<Slika>(0);
     private Set<Soba> sobas = new HashSet<Soba>(0);

    public Hotel() {
    }

	
    public Hotel(Destinacija destinacija, String imeHotela, int zvezdice, String opisHotela) {
        this.destinacija = destinacija;
        this.imeHotela = imeHotela;
        this.zvezdice = zvezdice;
        this.opisHotela = opisHotela;
    }
    public Hotel(Destinacija destinacija, String imeHotela, int zvezdice, String opisHotela, Set<CenaSobe> cenaSobes, Set<Slika> slikas, Set<Soba> sobas) {
       this.destinacija = destinacija;
       this.imeHotela = imeHotela;
       this.zvezdice = zvezdice;
       this.opisHotela = opisHotela;
       this.cenaSobes = cenaSobes;
       this.slikas = slikas;
       this.sobas = sobas;
    }
   
    public Integer getIdHotela() {
        return this.idHotela;
    }
    
    public void setIdHotela(Integer idHotela) {
        this.idHotela = idHotela;
    }
    public Destinacija getDestinacija() {
        return this.destinacija;
    }
    
    public void setDestinacija(Destinacija destinacija) {
        this.destinacija = destinacija;
    }
    public String getImeHotela() {
        return this.imeHotela;
    }
    
    public void setImeHotela(String imeHotela) {
        this.imeHotela = imeHotela;
    }
    public int getZvezdice() {
        return this.zvezdice;
    }
    
    public void setZvezdice(int zvezdice) {
        this.zvezdice = zvezdice;
    }
    public String getOpisHotela() {
        return this.opisHotela;
    }
    
    public void setOpisHotela(String opisHotela) {
        this.opisHotela = opisHotela;
    }
    public Set<CenaSobe> getCenaSobes() {
        return this.cenaSobes;
    }
    
    public void setCenaSobes(Set<CenaSobe> cenaSobes) {
        this.cenaSobes = cenaSobes;
    }
    public Set<Slika> getSlikas() {
        return this.slikas;
    }
    
    public void setSlikas(Set<Slika> slikas) {
        this.slikas = slikas;
    }
    public Set<Soba> getSobas() {
        return this.sobas;
    }
    
    public void setSobas(Set<Soba> sobas) {
        this.sobas = sobas;
    }




}


