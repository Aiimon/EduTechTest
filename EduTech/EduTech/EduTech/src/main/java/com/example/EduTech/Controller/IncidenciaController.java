package com.example.EduTech.Controller;

import com.example.EduTech.Model.Incidencia;
import com.example.EduTech.Service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping
    public List<Incidencia> listarIncidencias() {
        return incidenciaService.listarIncidencias();
    }

    @GetMapping("/usuario/{id}")
    public List<Incidencia> listarPorUsuario(@PathVariable Long usuarioId) {
        return incidenciaService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> obtenerIncidenciaPorId(@PathVariable Long id) {
        return incidenciaService.getIncidenciaById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Incidencia> crearIncidencia(@RequestBody Incidencia incidencia) {
        Incidencia creado = incidenciaService.guardarIncidencia(incidencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIncidencia(@PathVariable Long id) {
        if (incidenciaService.getIncidenciaById(id).isPresent()) {
            incidenciaService.eliminarIncidencia(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

