package com.example.EduTech.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EduTech.Model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
