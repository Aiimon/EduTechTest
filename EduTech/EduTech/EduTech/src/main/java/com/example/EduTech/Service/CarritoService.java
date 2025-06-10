package com.example.EduTech.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EduTech.Model.Curso;
import com.example.EduTech.Repository.CursoRepository;

@Service
public class CarritoService {
    
    private List<Curso> carrito = new ArrayList<>();

    @Autowired
    private CursoRepository cursoRepository;

    public void agregarCurso(Long idCurso) {
        Curso curso = cursoRepository.findById(idCurso).orElseThrow();
        carrito.add(curso);
    }

    public List<Curso> listarCursos() {
        return carrito;
    }

    public void eliminarCurso(Long idCurso) {
        carrito.removeIf(curso -> curso.getId().equals(idCurso));
    }

    public void vaciarCarrito() {
        carrito.clear();
    }
}