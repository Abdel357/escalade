package com.example.jeeProjet.repo;

import com.example.jeeProjet.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    // Permet d'obtenir une catégorie avec ses sorties
    // Optional nous permet de gérer les cas ou la méthode ne trouve rien
    @Query("SELECT c FROM Categorie c JOIN FETCH c.sorties WHERE c.id = :id")
    Optional<Categorie> findCategoriesBySorties(@Param("id") Long id);

}

