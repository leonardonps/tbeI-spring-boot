package com.alunoonline.api.service;

import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository repository;

    public Disciplina create (Disciplina disciplina){
        return repository.save(disciplina);
    }

    public Disciplina update(Disciplina disciplina){
        Optional<Disciplina> disciplinaEncontrada = repository.findById(disciplina.getId());
        disciplinaEncontrada.get().setNome(disciplina.getNome());
        disciplinaEncontrada.get().getProfessor().setNome(disciplina.getProfessor().getNome());
        disciplinaEncontrada.get().getProfessor().setEmail(disciplina.getProfessor().getEmail());

        return repository.save(disciplinaEncontrada.get());
    }

    public List<Disciplina> findAll(){
        return repository.findAll();
    }

    public Optional<Disciplina> findById(Long id){
        return repository.findById(id);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

}
