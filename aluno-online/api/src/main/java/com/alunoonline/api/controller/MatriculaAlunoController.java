package com.alunoonline.api.controller;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.AtualizarNotasRequestDto;
import com.alunoonline.api.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matricula-aluno")
public class MatriculaAlunoController {

    @Autowired
    MatriculaAlunoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MatriculaAluno> create (@RequestBody MatriculaAluno matriculaAluno) {
        MatriculaAluno matriculaAlunoCreated = service.create(matriculaAluno);
        return ResponseEntity.status(201).body(matriculaAlunoCreated);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MatriculaAluno> findAll(){
        return service.findAll();
    }

    @GetMapping("/{matriculaAlunoId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<MatriculaAluno> findById(@PathVariable Long matriculaAlunoId){
        return service.findById(matriculaAlunoId);
    }

    @DeleteMapping("/{matriculaAlunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long matriculaAlunoId){
        service.deleteById(matriculaAlunoId);
    }


    @PutMapping("/atualizar/{matriculaAlunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upgrade(@PathVariable Long matriculaAlunoId,
                                      @RequestBody MatriculaAluno matriculaAluno){
        service.update(matriculaAlunoId, matriculaAluno);
    }

    @PatchMapping("/atualizar-notas/{matriculaAlunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upgradeGrades (@PathVariable Long matriculaAlunoId,
                               @RequestBody AtualizarNotasRequestDto atualizarNotasRequestDto) {
        service.updateGrades(atualizarNotasRequestDto, matriculaAlunoId);
    }

    @PatchMapping("/trancar-aluno/{matriculaAlunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusToBreak(@PathVariable Long matriculaAlunoId){
        service.updateStatusToBreak(matriculaAlunoId);
    }
}
