package com.example.EduTech.Controller;

import com.example.EduTech.Model.Curso;
import com.example.EduTech.Service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable Long id) {
        return cursoService.getCursoById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody Curso curso) {
        Curso cursoCreado = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoCreado);
    }

    @PutMapping("/{id}/reducir-cupo")
    public ResponseEntity<String> reducirCupo(@PathVariable Long id){
        boolean actualizado = cursoService.reducirCupoDisponible(id);
        if (actualizado) {
            return ResponseEntity.ok("Cupo reducido!");
        }else{
            return ResponseEntity.badRequest().body("No hay cupos disponibles");
        }
    }

    @PutMapping("/{id}/restaurar-cupo")
    public ResponseEntity<String> restaurarCupo(@PathVariable Long id){
        boolean restaurado = cursoService.restaurarCupoDisponible(id);
        if (restaurado) {
            return ResponseEntity.ok("Cupo restaurado!");
        } else {
            return ResponseEntity.badRequest().body("No se pudo restaurar el cupo");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        if (cursoService.getCursoById(id).isPresent()) {
            cursoService.eliminarCurso(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}