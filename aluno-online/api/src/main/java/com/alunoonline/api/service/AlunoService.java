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

    public Aluno update(Aluno aluno){
        Optional<Aluno> alunoEncontrado = repository.findById(aluno.getId());
        alunoEncontrado.get().setNome(aluno.getNome());
        alunoEncontrado.get().setEmail(aluno.getEmail());
        alunoEncontrado.get().setCurso(aluno.getCurso());

        return repository.save(alunoEncontrado.get());
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
