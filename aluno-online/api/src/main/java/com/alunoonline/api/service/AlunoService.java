package com.alunoonline.api.service;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository repository;

    public Aluno create (Aluno aluno){
        return repository.save(aluno);
    }

    public void update(Long id, Aluno novoAlunoInfo){

        Optional<Aluno> alunoToUpdate = repository.findById(id);

        if(alunoToUpdate.isPresent()) {

            if (novoAlunoInfo.getNome() != null) {
                alunoToUpdate.get().setNome(novoAlunoInfo.getNome());
            }

            if (novoAlunoInfo.getEmail() != null) {
                alunoToUpdate.get().setEmail(novoAlunoInfo.getEmail());
            }

            if (novoAlunoInfo.getCurso() != null) {
                alunoToUpdate.get().setNome(novoAlunoInfo.getCurso());
            }

            repository.save(alunoToUpdate.get());
        }
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
}
