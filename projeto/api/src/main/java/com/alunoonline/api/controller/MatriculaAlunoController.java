package com.alunoonline.api.controller;

import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.HistoricoAlunoDto;
import com.alunoonline.api.model.dtos.MatriculaAlunoNotasOnlyDto;
import com.alunoonline.api.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matricula-aluno")
public class MatriculaAlunoController {

    @Autowired
    MatriculaAlunoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MatriculaAluno> create (@RequestBody MatriculaAluno matriculaAluno){
        MatriculaAluno matriculaAlunoCreated = service.create(matriculaAluno);

        return ResponseEntity.status(201).body(matriculaAlunoCreated);
    }

    @PatchMapping("/update-grades/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGrades(@RequestBody MatriculaAlunoNotasOnlyDto notasOnlyDto,
                      @PathVariable Long id){
        service.updateGrades(notasOnlyDto, id);
    }

    @PatchMapping("/atualiza-status/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusToLocked(@PathVariable Long id){
        service.updateStatusToLocked(id);
    }

    @GetMapping("/historico-aluno/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HistoricoAlunoDto getHistoricoFromAluno(@PathVariable Long id){
        return service.getHistoricoFromAuno(id);
    }
}
