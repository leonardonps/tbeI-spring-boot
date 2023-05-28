package com.alunoonline.api.service;

import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.DisciplinaRepository;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository repositoryDisciplina;
    @Autowired
    ProfessorRepository repositoryProfessor;

    public Disciplina create (Disciplina disciplina){
        return repositoryDisciplina.save(disciplina);
    }

    public void update(Long id, Disciplina novoDisciplinaInfo){
        Optional<Disciplina> disciplinaToUpdate= repositoryDisciplina.findById(id);

        if(disciplinaToUpdate.isPresent()){

            if(novoDisciplinaInfo.getNome() != null){
                disciplinaToUpdate.get().setNome(novoDisciplinaInfo.getNome());
            }

            if(novoDisciplinaInfo.getProfessor() != null){
                Long novoProfessorId = novoDisciplinaInfo.getProfessor().getId();
                Optional<Professor> novoProfessor = repositoryProfessor.findById(novoProfessorId);

                if(novoProfessor.isPresent()){
                    disciplinaToUpdate.get().setProfessor(novoProfessor.get());
                }
            }

            repositoryDisciplina.save(disciplinaToUpdate.get());
        }

    }

    public List<Disciplina> findAll(){
        return repositoryDisciplina.findAll();
    }

    public Optional<Disciplina> findById(Long id){
        return repositoryDisciplina.findById(id);
    }

    public void deleteById(Long id){
        repositoryDisciplina.deleteById(id);
    }

}
