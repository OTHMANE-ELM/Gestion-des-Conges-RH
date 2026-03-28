package com.example.rh.Service;

import com.example.rh.Repository.TypeCongeRepository;
import com.example.rh.entity.TypeConge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeCongeService {

    @Autowired
    private TypeCongeRepository typeCongeRepository;

    public List<TypeConge> getAll() {
        return typeCongeRepository.findAll();
    }

    public TypeConge getById(Long id) {
        return typeCongeRepository.findById(id).orElse(null);
    }

    public TypeConge save(TypeConge typeConge) {
        return typeCongeRepository.save(typeConge);
    }

    public void delete(Long id) {
        typeCongeRepository.deleteById(id);
    }
}
