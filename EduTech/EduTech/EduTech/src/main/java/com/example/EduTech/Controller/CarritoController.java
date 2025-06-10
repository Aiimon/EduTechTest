package com.example.EduTech.Controller;

import com.example.EduTech.Model.Curso;
import com.example.EduTech.Service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {

    private final List<Curso> carrito = new ArrayList<>();

    @Autowired
    private CursoService cursoserv;

    @PostMapping("/agregar/{idCurso}")
public String agregarCurso(@PathVariable Long idCurso) {
    Curso curso = cursoserv.getCursoID(idCurso);
    if (curso != null) {
        System.out.println("CURSO ENCONTRADO: " + curso);  // 👈 DEBUG
        carrito.add(curso);
        return "El curso se agregó al carrito: " + curso.getNombre();
    }
    return "El curso no fue encontrado";
}


    @DeleteMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable int id) {
        boolean eliminado = carrito.removeIf(curso -> curso.getId() == id);
        return eliminado ? "Curso ha sido eliminado" : "Curso no encontrado";
    }

    @GetMapping
public List<Curso> verCarrito() {
    for (Curso curso : carrito) {
        if (curso == null) {
            throw new RuntimeException("Curso nulo en carrito");
        }
    }
    return carrito;
}


    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "El carrito está vacío";
    }

    @GetMapping("/total")
    public int totalCursosCarrito() {
        return carrito.size();
    }
}
