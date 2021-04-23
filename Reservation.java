/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nour
 */
@Entity
public class Reservation implements Serializable {
    @Id
     private Integer id_reservation;
    private Integer id_event;
    private Integer id_user;
  private String nom;
  private String prenom;
  private Integer cin; 
  private String mail;

    public Reservation() {
    }

    public Reservation(Integer id_reservation, Integer id_event, Integer id_user, String nom, String prenom, Integer cin, String mail) {
        this.id_reservation = id_reservation;
        this.id_event = id_event;
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.mail = mail;
      
    }

    public Integer getId_reservation() {
        return id_reservation;
    }

    public Integer getId_event() {
        return id_event;
    }

    public Integer getId_user() {
        return id_user;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Integer getCin() {
        return cin;
    }

    public String getMail() {
        return mail;
    }

   

    public void setId_reservation(Integer id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setId_event(Integer id_event) {
        this.id_event = id_event;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


  
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_reservation != null ? id_reservation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        return !((this.id_reservation == null && other.id_reservation != null) || (this.id_reservation != null && !this.id_reservation.equals(other.id_reservation)));
    }

    @Override
    public String toString() {
        return '}' +"Reserver{" + "id_reservation=" + id_reservation + ", id_event=" + id_event + ", id_user=" + id_user + ", nom=" + nom + ", prenom =" + prenom + ",cin=" +cin + ",mail=" +mail;

   
    
}

}