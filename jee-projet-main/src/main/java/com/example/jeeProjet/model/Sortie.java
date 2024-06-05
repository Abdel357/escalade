package com.example.jeeProjet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sorties")
public class Sortie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    private String siteWeb;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSortie;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_id")
    private Membre createur;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    public Sortie(String nom, Date dateSortie, Membre createur, Categorie categorie) {
        this.nom = nom;
        this.dateSortie = dateSortie;
        this.createur = createur;
        this.categorie = categorie;
    }

    // Getters et setters

    public Categorie getCategorie() {
        return categorie;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public Long getId() {
        return id;
    }

    public Membre getCreateur() {
        return createur;
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setCreateur(Membre createur) {
        this.createur = createur;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }
}
