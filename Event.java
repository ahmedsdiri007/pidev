/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author nour
 */
@Entity
public class Event implements Serializable  {

    @Id
    private Integer id_event;
  private String description;
  private Integer nb_place;
  private String lieux;
  private Float prix; 

    public Event(Integer id_event, String description, Integer nb_place, String lieux, Float prix) {
        this.id_event = id_event;
        this.description = description;
        this.nb_place = nb_place;
        this.lieux = lieux;
        this.prix = prix;
    }

    public Integer getId_event() {
        return id_event;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNb_place() {
        return nb_place;
    }

    public String getLieux() {
        return lieux;
    }

    public Float getPrix() {
        return prix;
    }

    public void setId_event(Integer id_event) {
        this.id_event = id_event;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNb_place(Integer nb_place) {
        this.nb_place = nb_place;
    }

    public void setLieux(String lieux) {
        this.lieux = lieux;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }
  
  


    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        return !((this.id_event == null && other.id_event != null) || (this.id_event != null && !this.id_event.equals(other.id_event)));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id_event);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.nb_place);
        hash = 37 * hash + Objects.hashCode(this.lieux);
        hash = 37 * hash + Objects.hashCode(this.prix);
        return hash;
    }

    @Override
    public String toString() {
        return "Entity.Event{" + "id=" + id_event + ", description=" + description + ", nb_place=" + nb_place + ", lieux=" + lieux + ", prix=" + prix + '}';
    }
    
}
