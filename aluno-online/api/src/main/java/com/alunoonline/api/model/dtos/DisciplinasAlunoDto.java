package com.alunoonline.api.model.dtos;

import com.alunoonline.api.model.enums.StatusMatriculaAluno;
import lombok.Data;

@Data
public class DisciplinasAlunoDto {
    private String nomeDisciplina;
    private String professorDisciplina;
    private Double nota1;
    private Double nota2;
    private Double media;
    private StatusMatriculaAluno status;

}
