package com.alunoonline.api.service;

import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository repository;

    public Professor create(Professor professor){
        return repository.save(professor);
    }

    public void update(Long id, Professor novoProfessorInfo){
        Optional<Professor> professorToUpdate = repository.findById(id);

        if(professorToUpdate.isPresent()) {

            if(novoProfessorInfo.getNome() != null) {
                professorToUpdate.get().setNome(novoProfessorInfo.getNome());
            }

            if(novoProfessorInfo.getEmail() != null) {
                professorToUpdate.get().setEmail(novoProfessorInfo.getEmail());
            }

        }
         repository.save(professorToUpdate.get());
    }

    public List<Professor> findAll(){
        return repository.findAll();
    }

    public Optional<Professor> findById(Long id){
        return repository.findById(id);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
