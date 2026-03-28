package com.example.rh.Repository;

import com.example.rh.entity.DemandeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    List<DemandeConge> findByStatut(String statut);

    List<DemandeConge> findByEmploye_Departement(String departement);

    List<DemandeConge> findByTypeConge_Id(Long typeCongeId);

    List<DemandeConge> findByDateDebutBetween(LocalDate debut, LocalDate fin);

    List<DemandeConge> findByStatutAndEmploye_Departement(String statut, String departement);

    List<DemandeConge> findByStatutAndTypeConge_Id(String statut, Long typeCongeId);

    List<DemandeConge> findByStatutAndDateDebutBetween(String statut, LocalDate debut, LocalDate fin);

    // Statistiques : jours consommés par département (uniquement acceptées)
    @Query("SELECT e.departement, SUM(d.nombreJours) FROM DemandeConge d JOIN d.employe e WHERE d.statut = 'accepté' GROUP BY e.departement")
    List<Object[]> joursConsommesParDepartement();

    // Statistiques : taux d'acceptation global
    @Query("SELECT COUNT(d) FROM DemandeConge d WHERE d.statut = 'accepté'")
    long countAcceptees();

    @Query("SELECT COUNT(d) FROM DemandeConge d WHERE d.statut != 'en attente'")
    long countTraitees();

    // Quota consommé par employé et type de congé
    @Query("SELECT SUM(d.nombreJours) FROM DemandeConge d WHERE d.employe.id = :employeId AND d.typeConge.id = :typeCongeId AND d.statut = 'accepté'")
    Integer joursConsommesParEmployeEtType(Long employeId, Long typeCongeId);
}
