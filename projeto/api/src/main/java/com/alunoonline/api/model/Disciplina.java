package com.alunoonline.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Disciplina implements Serializable {

    private String nome;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

}
