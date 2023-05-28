package com.alunoonline.api.service;

import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.DisciplinasAlunoDto;
import com.alunoonline.api.model.dtos.HistoricoAlunoDto;
import com.alunoonline.api.model.dtos.MatriculaAlunoNotasOnlyDto;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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

    public void updateStatusToLocked(Long matriculaAlunoId){
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaAlunoId);

        if(matriculaAlunoToUpdate.isPresent()){
            MatriculaAluno matriculaAluno = matriculaAlunoToUpdate.get();

            String currentStatus = matriculaAluno.getStatus();

            if(currentStatus.equals("MATRICULADO")){
                matriculaAluno.setStatus("TRANCADO");
            } else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Só é possível trancar uma matrícula com status MATRICULADO");
            }

            repository.save(matriculaAluno);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada");
        }
    }

    public HistoricoAlunoDto getHistoricoFromAuno(Long alunoId){
        List<MatriculaAluno> matriculasAluno = repository.findByAlunoId(alunoId);

        if(!matriculasAluno.isEmpty()){
            HistoricoAlunoDto historico = new HistoricoAlunoDto();

            historico.setNomeAluno(matriculasAluno.get(0).getAluno().getNome());
            historico.setCurso(matriculasAluno.get(0).getAluno().getCurso());

            List<DisciplinasAlunoDto> disciplinasLista = new ArrayList<>();

            for (MatriculaAluno matriculaAluno : matriculasAluno){
                DisciplinasAlunoDto disciplinasAlunoDto = new DisciplinasAlunoDto();

                disciplinasAlunoDto.setNomeDisciplina(matriculaAluno.getDisciplina().getNome());
                disciplinasAlunoDto.setNomeProfessorDisciplina(matriculaAluno.
                        getDisciplina().getProfessor().getNome());
                disciplinasAlunoDto.setNota1(matriculaAluno.getNota1());
                disciplinasAlunoDto.setNota2(matriculaAluno.getNota2());

                if(matriculaAluno.getNota1() != null && matriculaAluno.getNota2() != null){
                    disciplinasAlunoDto.setMedia((matriculaAluno.getNota1() + matriculaAluno.getNota2())/2);
                } else {
                    disciplinasAlunoDto.setMedia(null);
                }

                disciplinasAlunoDto.setStatus(matriculaAluno.getStatus());
                disciplinasLista.add(disciplinasAlunoDto);
            }

            historico.setDisciplinasAlunoDtoList((disciplinasLista));

            return historico;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui disciplinas matriculadas.");
    }

}
