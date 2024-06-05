package com.example.jeeProjet.repo;

import com.example.jeeProjet.model.Sortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SortieRepository extends JpaRepository<Sortie, Long> {

    // Chercher des sorties en fonction du nom
    List<Sortie> findByNomContaining(String nom);

    //TODO rajouter d'autre critère de recherche

}

