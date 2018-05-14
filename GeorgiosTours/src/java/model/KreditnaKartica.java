package model;
// Generated Sep 2, 2017 3:19:43 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * KreditnaKartica generated by hbm2java
 */
public class KreditnaKartica  implements java.io.Serializable {


     private String brojKartice;
     private String tipKartice;
     private String imeIPrezimeNaKartici;
     private String datumIsteka;
     private String cvc;
     private double stanjeNaKartici;
     private Set<Putnik> putniks = new HashSet<Putnik>(0);

    public KreditnaKartica() {
    }

	
    public KreditnaKartica(String brojKartice, String tipKartice, String imeIPrezimeNaKartici, String datumIsteka, String cvc, double stanjeNaKartici) {
        this.brojKartice = brojKartice;
        this.tipKartice = tipKartice;
        this.imeIPrezimeNaKartici = imeIPrezimeNaKartici;
        this.datumIsteka = datumIsteka;
        this.cvc = cvc;
        this.stanjeNaKartici = stanjeNaKartici;
    }
    public KreditnaKartica(String brojKartice, String tipKartice, String imeIPrezimeNaKartici, String datumIsteka, String cvc, double stanjeNaKartici, Set<Putnik> putniks) {
       this.brojKartice = brojKartice;
       this.tipKartice = tipKartice;
       this.imeIPrezimeNaKartici = imeIPrezimeNaKartici;
       this.datumIsteka = datumIsteka;
       this.cvc = cvc;
       this.stanjeNaKartici = stanjeNaKartici;
       this.putniks = putniks;
    }
   
    public String getBrojKartice() {
        return this.brojKartice;
    }
    
    public void setBrojKartice(String brojKartice) {
        this.brojKartice = brojKartice;
    }
    public String getTipKartice() {
        return this.tipKartice;
    }
    
    public void setTipKartice(String tipKartice) {
        this.tipKartice = tipKartice;
    }
    public String getImeIPrezimeNaKartici() {
        return this.imeIPrezimeNaKartici;
    }
    
    public void setImeIPrezimeNaKartici(String imeIPrezimeNaKartici) {
        this.imeIPrezimeNaKartici = imeIPrezimeNaKartici;
    }
    public String getDatumIsteka() {
        return this.datumIsteka;
    }
    
    public void setDatumIsteka(String datumIsteka) {
        this.datumIsteka = datumIsteka;
    }
    public String getCvc() {
        return this.cvc;
    }
    
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
    public double getStanjeNaKartici() {
        return this.stanjeNaKartici;
    }
    
    public void setStanjeNaKartici(double stanjeNaKartici) {
        this.stanjeNaKartici = stanjeNaKartici;
    }
    public Set<Putnik> getPutniks() {
        return this.putniks;
    }
    
    public void setPutniks(Set<Putnik> putniks) {
        this.putniks = putniks;
    }




}


