/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Maryem Cherif
 */
public class resto {

  
    private int id_resto;
    private int nbplace;
    private int budget;
    private String nomresto;
    private String restopic;
    private int idCategorie;

    public resto(int id_resto, int nbplace, int budget, String nomresto, String restopic, int idCategorie) {
        this.id_resto = id_resto;
        this.nbplace = nbplace;
        this.budget = budget;
        this.nomresto = nomresto;
        this.restopic = restopic;
        this.idCategorie = idCategorie;
    }

    public resto(int nbplace, int budget, String nomresto, String restopic, int idCategorie) {
        this.nbplace = nbplace;
        this.budget = budget;
        this.nomresto = nomresto;
        this.restopic = restopic;
        this.idCategorie = idCategorie;
    }

    public resto() {
    }

    

   

  

    public void setId_resto(int id_resto) {
        this.id_resto = id_resto;
    }

    public void setNbplace(int nbplace) {
        this.nbplace = nbplace;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getNomresto() {
        return nomresto;
    }

    public void setNomresto(String nomresto) {
        this.nomresto = nomresto;
    }

    

   

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getId_resto() {
        return id_resto;
    }

    public int getNbplace() {
        return nbplace;
    }

    public int getBudget() {
        return budget;
    }

    public String getRestopic() {
        return restopic;
    }

    public void setRestopic(String restopic) {
        this.restopic = restopic;
    }

   

  

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getRestopic(String photo) {
        return restopic;
    }
}
