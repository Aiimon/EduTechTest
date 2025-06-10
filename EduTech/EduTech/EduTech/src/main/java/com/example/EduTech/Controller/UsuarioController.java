package com.example.EduTech.Controller;

import com.example.EduTech.Model.Usuario;
import com.example.EduTech.Service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private UsuarioService serv;

    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario u) {
        return serv.registrar(u); 
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Usuario u) {
        Optional<Usuario> user = serv.autenticar(u.getEmail(), u.getPassword());
        Map<String, String> response = new HashMap<>();
        if (user.isPresent()) {
            response.put("result", "OK");
            response.put("nombre", user.get().getNombre());
        }else{
            response.put("result", "ERROR");
        }
        
        return response;
    }
    
}