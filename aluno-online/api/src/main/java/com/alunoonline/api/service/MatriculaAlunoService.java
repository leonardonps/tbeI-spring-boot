package com.alunoonline.api.service;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.AtualizarNotasRequestDto;
import com.alunoonline.api.model.dtos.DisciplinasAlunoDto;
import com.alunoonline.api.model.dtos.HistoricoAlunoDto;
import com.alunoonline.api.model.enums.StatusMatriculaAluno;
import com.alunoonline.api.repository.AlunoRepository;
import com.alunoonline.api.repository.DisciplinaRepository;
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

    static final Double GRADEAVGTOAPPROVE = 7d;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public MatriculaAluno create(MatriculaAluno matriculaAluno){
        return matriculaAlunoRepository.save(matriculaAluno);
    }

    public void update(Long matriculaAlunoId, MatriculaAluno novoMatriculaAlunoInfo){
        Optional<MatriculaAluno> matriculaAlunoToUpdate = matriculaAlunoRepository.findById(matriculaAlunoId);

        if(matriculaAlunoToUpdate.isPresent()){

            if(novoMatriculaAlunoInfo.getAluno() != null){
                Long novoAlunoId = novoMatriculaAlunoInfo.getAluno().getId();
                Optional<Aluno> novoAluno = alunoRepository.findById(novoAlunoId);

                if(novoAluno.isPresent()){
                    matriculaAlunoToUpdate
                        .get()
                        .setAluno(novoAluno.get());
                }
            }

            if(novoMatriculaAlunoInfo.getDisciplina() != null){
                Long novoDisciplinaId = novoMatriculaAlunoInfo.getDisciplina().getId();
                Optional<Disciplina> novaDisciplina = disciplinaRepository.findById(novoDisciplinaId);

                if(novaDisciplina.isPresent()) {
                    matriculaAlunoToUpdate
                            .get()
                            .setDisciplina(novaDisciplina.get());
                }
            }

            if(novoMatriculaAlunoInfo.getNota1() != null){
                matriculaAlunoToUpdate
                        .get()
                        .setNota1(novoMatriculaAlunoInfo.getNota1());
            }

            if(novoMatriculaAlunoInfo.getNota2() != null){
                matriculaAlunoToUpdate
                        .get()
                        .setNota2(novoMatriculaAlunoInfo.getNota2());
            }

            if(novoMatriculaAlunoInfo.getStatus() != null){
                matriculaAlunoToUpdate
                        .get()
                        .setStatus(novoMatriculaAlunoInfo.getStatus());
            }

            matriculaAlunoRepository.save(matriculaAlunoToUpdate.get());
        }
    }


    public List<MatriculaAluno> findAll(){
        return matriculaAlunoRepository.findAll();
    }

    public Optional<MatriculaAluno> findById(Long matriculaAlunoId){
        return matriculaAlunoRepository.findById(matriculaAlunoId);
    }

    public void deleteById(Long matriculaAlunoId){
        matriculaAlunoRepository.deleteById(matriculaAlunoId);
    }

    public void updateGrades(AtualizarNotasRequestDto atualizarNotasRequestDto,
                             Long matriculaAlunoId){

        Optional<MatriculaAluno> matriculaAlunoToUpdate = matriculaAlunoRepository.findById(matriculaAlunoId);

        if(matriculaAlunoToUpdate.isPresent()
                && !matriculaAlunoToUpdate.get().getStatus().equals(StatusMatriculaAluno.TRANCADO)){

            if(atualizarNotasRequestDto.getNota1() != null){
                matriculaAlunoToUpdate.get().setNota1(atualizarNotasRequestDto.getNota1());
            }

            if(atualizarNotasRequestDto.getNota2() != null){
                matriculaAlunoToUpdate.get().setNota2(atualizarNotasRequestDto.getNota2());
            }

            if(atualizarNotasRequestDto.getNota1() != null
                    && atualizarNotasRequestDto.getNota2() != null) {

                matriculaAlunoToUpdate.get().setStatus(
                        this.classifyAverageToStatus(
                            this.doAverage(
                                    this.addGradesInArrayList(
                                        matriculaAlunoToUpdate.get().getNota1(),
                                        matriculaAlunoToUpdate.get().getNota2()
                                )
                            )
                        )
                );
            }


            matriculaAlunoRepository.save(matriculaAlunoToUpdate.get());

        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Matrícula não encontrada ou trancada.");
        }
    }

    public void updateStatusToBreak(Long matriculaAlunoId){
        Optional<MatriculaAluno> matriculaAlunoToUpdate = matriculaAlunoRepository.findById(matriculaAlunoId);

        if(matriculaAlunoToUpdate.isPresent()){
            StatusMatriculaAluno currentStatus = matriculaAlunoToUpdate.get().getStatus();

            if(currentStatus.equals(StatusMatriculaAluno.MATRICULADO)){
                matriculaAlunoToUpdate.get().setStatus(StatusMatriculaAluno.TRANCADO);
                matriculaAlunoRepository.save(matriculaAlunoToUpdate.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Só é possível trancar uma matrícula com status MATRICULADO.");
            }

        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Matrícula não encontrada.");
        }
    }

    public HistoricoAlunoDto getHistoricoFromAluno(Long alunoId){
        List<MatriculaAluno> matriculasAluno = matriculaAlunoRepository.findByAlunoId(alunoId);

        Optional<Aluno> aluno = alunoRepository.findById(alunoId);

        if(aluno.isPresent()){
            HistoricoAlunoDto historicoAlunoDto = new HistoricoAlunoDto();

            historicoAlunoDto.setNomeAluno(aluno.get().getNome());
            historicoAlunoDto.setCursoAluno(aluno.get().getCurso());

            if(!matriculasAluno.isEmpty()){

                List<DisciplinasAlunoDto> disciplinasList = new ArrayList<>();

                for(MatriculaAluno matriculaAluno : matriculasAluno){

                    DisciplinasAlunoDto disciplinasAlunoDto = new DisciplinasAlunoDto();

                    disciplinasAlunoDto.setNomeDisciplina(matriculaAluno.getDisciplina().getNome());
                    disciplinasAlunoDto.setProfessorDisciplina(matriculaAluno.getDisciplina().getProfessor().getNome());
                    disciplinasAlunoDto.setNota1(matriculaAluno.getNota1());
                    disciplinasAlunoDto.setNota2(matriculaAluno.getNota2());


                    if(matriculaAluno.getNota1() != null
                            && matriculaAluno.getNota2() != null) {
                        disciplinasAlunoDto.setMedia(
                                this.doAverage(
                                        this.addGradesInArrayList(
                                                matriculaAluno.getNota1(),
                                                matriculaAluno.getNota2()
                                        )
                                )
                        );
                    }

                    disciplinasAlunoDto.setStatus(matriculaAluno.getStatus());

                    disciplinasList.add(disciplinasAlunoDto);
                }

                historicoAlunoDto.setDisciplinasAlunoDtoList(disciplinasList);

                return historicoAlunoDto;

            }else{
                return historicoAlunoDto;
            }

        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        }
    }

    private List addGradesInArrayList(Double ... notas){
        List<Double> notasList = new ArrayList<>();


        for(Double nota : notas){
            notasList.add(nota);
        }

        return notasList;
    }

    private Double doAverage(List<Double> notas){
        Double somaTotal = 0d;

        for(Double nota : notas){
            somaTotal = somaTotal + nota;
        }

        return somaTotal/ notas.size();
    }

    private StatusMatriculaAluno classifyAverageToStatus(Double average){
        if(average >= GRADEAVGTOAPPROVE){
            return StatusMatriculaAluno.APROVADO;
        }else{
            return StatusMatriculaAluno.REPROVADO;
        }
    }

}
