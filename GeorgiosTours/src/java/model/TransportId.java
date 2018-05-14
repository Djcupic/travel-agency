package model;
// Generated Sep 2, 2017 3:19:43 PM by Hibernate Tools 4.3.1



/**
 * TransportId generated by hbm2java
 */
public class TransportId  implements java.io.Serializable {


     private int idDestinacije;
     private int idPrevoza;

    public TransportId() {
    }

    public TransportId(int idDestinacije, int idPrevoza) {
       this.idDestinacije = idDestinacije;
       this.idPrevoza = idPrevoza;
    }
   
    public int getIdDestinacije() {
        return this.idDestinacije;
    }
    
    public void setIdDestinacije(int idDestinacije) {
        this.idDestinacije = idDestinacije;
    }
    public int getIdPrevoza() {
        return this.idPrevoza;
    }
    
    public void setIdPrevoza(int idPrevoza) {
        this.idPrevoza = idPrevoza;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TransportId) ) return false;
		 TransportId castOther = ( TransportId ) other; 
         
		 return (this.getIdDestinacije()==castOther.getIdDestinacije())
 && (this.getIdPrevoza()==castOther.getIdPrevoza());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdDestinacije();
         result = 37 * result + this.getIdPrevoza();
         return result;
   }   


}


