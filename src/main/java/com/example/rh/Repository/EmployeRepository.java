package com.example.rh.Repository;

import com.example.rh.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByNomContainingIgnoreCase(String nom);
    List<Employe> findByDepartement(String departement);
}
