/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KENGNI Hippolyte
 */
@Entity
@Table(name = "operations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operations.findAll", query = "SELECT o FROM Operations o")
    , @NamedQuery(name = "Operations.findByIdoperations", query = "SELECT o FROM Operations o WHERE o.idoperations = :idoperations")
    , @NamedQuery(name = "Operations.findByNom", query = "SELECT o FROM Operations o WHERE o.nom = :nom")
    , @NamedQuery(name = "Operations.findByCible", query = "SELECT o FROM Operations o WHERE o.cible = :cible")
    , @NamedQuery(name = "Operations.findByDate", query = "SELECT o FROM Operations o WHERE o.date = :date")
    , @NamedQuery(name = "Operations.findByHeure", query = "SELECT o FROM Operations o WHERE o.heure = :heure")
    , @NamedQuery(name = "Operations.findByAdresseip", query = "SELECT o FROM Operations o WHERE o.adresseip = :adresseip")})
public class Operations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idoperations")
    private Integer idoperations;
    @Size(max = 254)
    @Column(name = "nom")
    private String nom;
    @Size(max = 254)
    @Column(name = "cible")
    private String cible;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "heure")
    @Temporal(TemporalType.TIME)
    private Date heure;
    @Size(max = 254)
    @Column(name = "adresseip")
    private String adresseip;
    @JoinColumn(name = "idutilisateur", referencedColumnName = "idutilisateur")
    @ManyToOne(optional = false)
    private Utilisateur idutilisateur;

    public Operations() {
    }

    public Operations(Integer idoperations) {
        this.idoperations = idoperations;
    }

    public Integer getIdoperations() {
        return idoperations;
    }

    public void setIdoperations(Integer idoperations) {
        this.idoperations = idoperations;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCible() {
        return cible;
    }

    public void setCible(String cible) {
        this.cible = cible;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public String getAdresseip() {
        return adresseip;
    }

    public void setAdresseip(String adresseip) {
        this.adresseip = adresseip;
    }

    public Utilisateur getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Utilisateur idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idoperations != null ? idoperations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operations)) {
            return false;
        }
        Operations other = (Operations) object;
        if ((this.idoperations == null && other.idoperations != null) || (this.idoperations != null && !this.idoperations.equals(other.idoperations))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Operations[ idoperations=" + idoperations + " ]";
    }
    
}
