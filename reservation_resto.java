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
public class reservation_resto {
    private int id_res_resto;
    private int id_user;
    private int nbpersonne;
    private String mail;
    private int numero;

    public reservation_resto(int id_res_resto, int id_user, int nbpersonne, String mail, int numero, int idResto) {
        this.id_res_resto = id_res_resto;
        this.id_user = id_user;
        this.nbpersonne = nbpersonne;
        this.mail = mail;
        this.numero = numero;
        this.idResto = idResto;
    }
    private int idResto;

    
    public reservation_resto(int nbpersonne, String mail, int numero) {
        this.nbpersonne = nbpersonne;
        this.mail = mail;
        this.numero = numero;
    }

    public reservation_resto(int id_res_resto, int nbpersonne, String mail, int numero) {
                this.id_res_resto = id_res_resto;
this.nbpersonne = nbpersonne;
        this.mail = mail;
        this.numero = numero;    }

    
   

   

    public int getId_res_resto() {
        return id_res_resto;
    }

    public int getId_user() {
        return id_user;
    }

    public int getNbpersonne() {
        return nbpersonne;
    }

   

    public int getNumero() {
        return numero;
    }

    public int getIdResto() {
        return idResto;
    }

    public void setId_res_resto(int id_res_resto) {
        this.id_res_resto = id_res_resto;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setNbpersonne(int nbpersonne) {
        this.nbpersonne = nbpersonne;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setIdResto(int idResto) {
        this.idResto = idResto;
    }
}
