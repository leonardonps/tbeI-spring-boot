package com.alunoonline.api.service;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.repository.AlunoRepository;
import jakarta.persistence.StoredProcedureParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository repository;

    public Aluno create(Aluno aluno){
        return repository.save(aluno);
    }

    public List<Aluno> findAll(){
        return repository.findAll();
    }

    public Optional<Aluno> findById(Long id){
        return repository.findById(id);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public void updateById(Long id){

    }
}
