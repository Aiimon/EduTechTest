package com.example.EduTech.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EduTech.Model.Curso;
import com.example.EduTech.Repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> listarTodos(){
        return cursoRepository.findAll();
    }

    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> getCursoById(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso getCursoID(Long id) {
    Optional<Curso> curso = cursoRepository.findById(id);
    return curso.orElse(null);
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    @Transactional
    public boolean reducirCupoDisponible(Long id) {
    Optional<Curso> cursoOpt = cursoRepository.findById(id);
    if (cursoOpt.isPresent()) {
        Curso curso = cursoOpt.get();
        if (curso.getCupoDisponible() > 0) {
            curso.setCupoDisponible(curso.getCupoDisponible() - 1);
            cursoRepository.save(curso);
            return true;
            }
        }
        return false;
    }


    public boolean restaurarCupoDisponible(Long id) {
    Optional<Curso> cursoOpt = cursoRepository.findById(id);
    if (cursoOpt.isPresent()) {
        Curso curso = cursoOpt.get();
        curso.setCupoDisponible(curso.getCupoDisponible() + 1);
        cursoRepository.save(curso);
        return true;
        }
    return false;
    }   


}
