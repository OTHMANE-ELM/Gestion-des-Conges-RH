package com.example.rh.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "types_conge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeConge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String libelle; // Congé annuel, Maladie, Maternité, Sans solde...

    @Column(nullable = false)
    private Integer quotaAnnuel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getQuotaAnnuel() {
        return quotaAnnuel;
    }

    public void setQuotaAnnuel(Integer quotaAnnuel) {
        this.quotaAnnuel = quotaAnnuel;
    }

    // Nombre de jours autorisés par an
}
