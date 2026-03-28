package com.example.rh.Service;

import com.example.rh.Repository.DemandeCongeRepository;
import com.example.rh.entity.DemandeConge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemandeCongeService {

    @Autowired
    private DemandeCongeRepository demandeCongeRepository;

    public List<DemandeConge> getAll() {
        return demandeCongeRepository.findAll();
    }

    public DemandeConge getById(Long id) {
        return demandeCongeRepository.findById(id).orElse(null);
    }

    public DemandeConge save(DemandeConge demande) {
        // Calcul automatique du nombre de jours
        if (demande.getDateDebut() != null && demande.getDateFin() != null) {
            long jours = ChronoUnit.DAYS.between(demande.getDateDebut(), demande.getDateFin());
            if (jours <= 0) jours = 1;
            demande.setNombreJours((int) jours);
        }
        // Statut par défaut
        if (demande.getStatut() == null || demande.getStatut().isEmpty()) {
            demande.setStatut("en attente");
        }
        return demandeCongeRepository.save(demande);
    }

    public void delete(Long id) {
        demandeCongeRepository.deleteById(id);
    }

    public List<DemandeConge> filtrer(String statut, String departement, Long typeCongeId, LocalDate debut, LocalDate fin) {
        if (statut != null && !statut.isEmpty() && departement != null && !departement.isEmpty()) {
            return demandeCongeRepository.findByStatutAndEmploye_Departement(statut, departement);
        }
        if (statut != null && !statut.isEmpty() && typeCongeId != null) {
            return demandeCongeRepository.findByStatutAndTypeConge_Id(statut, typeCongeId);
        }
        if (statut != null && !statut.isEmpty() && debut != null && fin != null) {
            return demandeCongeRepository.findByStatutAndDateDebutBetween(statut, debut, fin);
        }
        if (statut != null && !statut.isEmpty()) {
            return demandeCongeRepository.findByStatut(statut);
        }
        if (departement != null && !departement.isEmpty()) {
            return demandeCongeRepository.findByEmploye_Departement(departement);
        }
        if (typeCongeId != null) {
            return demandeCongeRepository.findByTypeConge_Id(typeCongeId);
        }
        if (debut != null && fin != null) {
            return demandeCongeRepository.findByDateDebutBetween(debut, fin);
        }
        return demandeCongeRepository.findAll();
    }

    public void mettreAJourStatut(Long id, String statut) {
        DemandeConge d = getById(id);
        if (d != null) {
            d.setStatut(statut);
            demandeCongeRepository.save(d);
        }
    }

    // Statistiques
    public Map<String, Integer> joursConsommesParDepartement() {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Object[] row : demandeCongeRepository.joursConsommesParDepartement()) {
            result.put((String) row[0], ((Number) row[1]).intValue());
        }
        return result;
    }

    public double tauxAcceptation() {
        long traitees = demandeCongeRepository.countTraitees();
        if (traitees == 0) return 0.0;
        return ((double) demandeCongeRepository.countAcceptees() / traitees) * 100;
    }

    public long countTotal() {
        return demandeCongeRepository.count();
    }

    public long countByStatut(String statut) {
        return demandeCongeRepository.findByStatut(statut).size();
    }

    public Integer quotaConsomme(Long employeId, Long typeCongeId) {
        Integer total = demandeCongeRepository.joursConsommesParEmployeEtType(employeId, typeCongeId);
        return total != null ? total : 0;
    }
}
