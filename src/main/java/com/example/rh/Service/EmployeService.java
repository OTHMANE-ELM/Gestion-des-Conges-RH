package com.example.rh.Service;

import com.example.rh.Repository.EmployeRepository;
import com.example.rh.entity.Employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> getAll() {
        return employeRepository.findAll();
    }

    public Employe getById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    public Employe save(Employe employe) {
        return employeRepository.save(employe);
    }

    public void delete(Long id) {
        employeRepository.deleteById(id);
    }

    public List<Employe> rechercher(String nom) {
        if (nom != null && !nom.isEmpty()) {
            return employeRepository.findByNomContainingIgnoreCase(nom);
        }
        return employeRepository.findAll();
    }

    public List<Employe> findByDepartement(String departement) {
        return employeRepository.findByDepartement(departement);
    }
}
