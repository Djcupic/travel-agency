package model;
// Generated Sep 2, 2017 3:19:43 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipSobe generated by hbm2java
 */
public class TipSobe  implements java.io.Serializable {


     private Integer idTipaSobe;
     private String tipSobe;
     private Set<Soba> sobas = new HashSet<Soba>(0);
     private Set<CenaSobe> cenaSobes = new HashSet<CenaSobe>(0);

    public TipSobe() {
    }

	
    public TipSobe(String tipSobe) {
        this.tipSobe = tipSobe;
    }
    public TipSobe(String tipSobe, Set<Soba> sobas, Set<CenaSobe> cenaSobes) {
       this.tipSobe = tipSobe;
       this.sobas = sobas;
       this.cenaSobes = cenaSobes;
    }
   
    public Integer getIdTipaSobe() {
        return this.idTipaSobe;
    }
    
    public void setIdTipaSobe(Integer idTipaSobe) {
        this.idTipaSobe = idTipaSobe;
    }
    public String getTipSobe() {
        return this.tipSobe;
    }
    
    public void setTipSobe(String tipSobe) {
        this.tipSobe = tipSobe;
    }
    public Set<Soba> getSobas() {
        return this.sobas;
    }
    
    public void setSobas(Set<Soba> sobas) {
        this.sobas = sobas;
    }
    public Set<CenaSobe> getCenaSobes() {
        return this.cenaSobes;
    }
    
    public void setCenaSobes(Set<CenaSobe> cenaSobes) {
        this.cenaSobes = cenaSobes;
    }




}


