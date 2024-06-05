package com.example.jeeProjet.repo;

import com.example.jeeProjet.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

    // Permet d'obtenir un membre particulier avec ses sorties
    // Optional nous permet de gérer les cas ou la méthode ne trouve rien
    @Query("SELECT m FROM Membre m JOIN FETCH m.sorties WHERE m.id = :id")
    Optional<Membre> findMembresBySorties(@Param("id") Long id);

}

