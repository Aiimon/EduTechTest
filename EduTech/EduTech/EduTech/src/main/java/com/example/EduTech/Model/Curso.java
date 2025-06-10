package com.example.EduTech.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    @Column(name = "duracion_curso")
    private String duracionCurso;
    private int precio;
    @Column(name = "cupo_disponible")
    private int cupoDisponible;
    private String imagen;

    @ManyToMany(mappedBy = "cursos")
    private List<Usuario> usuarios;
}
