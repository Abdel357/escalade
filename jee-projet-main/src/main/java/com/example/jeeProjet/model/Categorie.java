package com.example.jeeProjet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sortie> sorties = new HashSet<>();

    public Categorie(String nom) {
        this.nom = nom;
    }

    public void addSortie(Sortie sortie) {
        sorties.add(sortie);
        sortie.setCategorie(this);
    }

    public void removeSortie(Sortie sortie) {
        sorties.remove(sortie);
        sortie.setCategorie(null);
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Set<Sortie> getSorties() {
        return sorties;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSorties(Set<Sortie> sorties) {
        this.sorties = sorties;
    }
}
