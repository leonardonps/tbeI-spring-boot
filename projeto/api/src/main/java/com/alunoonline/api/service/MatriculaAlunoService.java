package com.alunoonline.api.service;

import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.MatriculaAlunoNotasOnlyDto;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatriculaAlunoService {

    static final Double GRADEAVGTOAPPROVE = 7.0;

    @Autowired
    MatriculaAlunoRepository repository;

    public MatriculaAluno create(MatriculaAluno matriculaAluno){
        return repository.save(matriculaAluno);
    }

    public void updateGrades(MatriculaAlunoNotasOnlyDto matriculaAlunoNotasOnlyDto, Long matriculaId) {
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaId);

        boolean needUpdate = false;


        if (matriculaAlunoNotasOnlyDto.getNota1() != null) {
            matriculaAlunoToUpdate.
                    ifPresent(matriculaAluno -> matriculaAluno.setNota1(matriculaAlunoNotasOnlyDto.getNota1()));
            needUpdate = true;

        }

        if (matriculaAlunoNotasOnlyDto.getNota2() != null) {
            matriculaAlunoToUpdate.
                    ifPresent(matriculaAluno -> matriculaAluno.setNota2(matriculaAlunoNotasOnlyDto.getNota2()));
            needUpdate = true;
        }

        if(needUpdate){
            if(matriculaAlunoToUpdate.get().getNota1() != null
                    && matriculaAlunoToUpdate.get().getNota2() != null){
                if((matriculaAlunoToUpdate.get().getNota1() + matriculaAlunoToUpdate.get().getNota2())/2
                        >= GRADEAVGTOAPPROVE){
                    matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setStatus("APROVADO"));
                }else{
                    matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setStatus("REPROVADO"));
                }
            }

            repository.save(matriculaAlunoToUpdate.get());
        }
    }

}
