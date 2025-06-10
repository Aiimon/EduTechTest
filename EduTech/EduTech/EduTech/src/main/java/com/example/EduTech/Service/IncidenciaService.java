package com.example.EduTech.Service;

import com.example.EduTech.Model.Incidencia;
import com.example.EduTech.Repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    public List<Incidencia> listarIncidencias() {
        return incidenciaRepository.findAll();
    }

    public List<Incidencia> listarPorUsuario(Long usuarioId) {
        return incidenciaRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Incidencia> getIncidenciaById(Long id) {
        return incidenciaRepository.findById(id);
    }

    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public void eliminarIncidencia(Long id) {
        incidenciaRepository.deleteById(id);
    }
}
