package com.example.EduTech.Controller;

//importar las clases del backend necesarias
import com.example.EduTech.Model.Usuario;
import com.example.EduTech.Service.UsuarioService;

//Importar clase ObjectMapper para convertir objetos a JSON
import com.fasterxml.jackson.databind.ObjectMapper;

//Impotar las anotaciones de las pruebas JUnit
import org.junit.jupiter.api.Test;

//Importar las anotaciones Spring para inyectar las dependencias Maven
import org.springframework.beans.factory.annotation.Autowired;

//Importar la anotacion para las pruebas de los controladores web
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

//Importar la anotacion para simular los beans de Spring
import org.springframework.boot.test.mock.mockito.MockBean;

//Import el tipo de contenido MediaType para las peticiones HTTP (Controller)
import org.springframework.http.MediaType;

//Importar MockMvc para realizar las peticiones HTTP
import org.springframework.test.web.servlet.MockMvc;

//Importar las clases necesarias para realizar las peticiones HTTP simuladas
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

//Importar las clases necesarias para verificar los resultados de las peticiones HTTP simuladas
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Importar any para simular los argumentos en los metodos del servicios de usuario
import static org.mockito.ArgumentMatchers.any;

//Import Mockito para simular el comportamiento de los metodos del servicio usuario con when
import static org.mockito.Mockito.when;

import java.util.Optional;

//Uar la anotacion WebMvc para crear una prueba a un controlador especifico (UsuarioController)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerIntegrationTest {
    //Inyectar MockMov para realiar las peticiones HTTP simuladas
    @Autowired
    private MockMvc mockMvc;

    //Simular el servicio de usuario
    @MockBean
    private UsuarioService usuarioService;

    //Usar ObjectMapper para covrtir los objetos a JSON
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrarUsuario_ReturnGuardar() throws Exception {
        Usuario newUser = new Usuario();
        newUser.setNombre("Camilo");
        newUser.setEmail("camilo@gmail.com");
        newUser.setPassword("1234"); //Establecer password del usuario simulado
        //Simular el comportamiento del metodo registrar de usuarioService con un usuario registrado
        when(usuarioService.registrar(any(Usuario.class)))
            .thenReturn(newUser);

        mockMvc.perform(post("/api/v1/usuarios/registrar")
            .contentType(MediaType.APPLICATION_JSON) //Establecer el tipo de contenido JSON
            .content(objectMapper.writeValueAsString(newUser))) //Convertir el usuario de simulacion a JSON
            .andExpect(status().isOk()) //Verificamos que la respuesta al metodo sea un esto 200=OK
            .andExpect(jsonPath("$.nombre").value("Camilo"))//Verificamos que el nombre del usuario simulado sea correcto
            .andExpect(jsonPath("$.email").value("camilo@gmail.com"))//Verificamos que el correo del usuario simulado sea correcto
            .andExpect(jsonPath("$.password").value("1234"));//Verificamos que la password del usuario simulado sea correcto
            }

    //Test para simular el inicio de sesion de un usuario registrado
    @Test
    void loginUsuario_ReturnOK()throws Exception{
        Usuario userExistente = new Usuario();
        userExistente.setNombre("Camilo");
        userExistente.setEmail("camilo@gmail.com");
        userExistente.setPassword("1234");

        //simular el comportamiento del metodo autenticar de usuarioService con usuario registrado
        when(usuarioService.autenticar("camilo@gmail.com", "1234"))
            .thenReturn(Optional.of(userExistente));
    

    //Simular la peticion POST en usuarioController para autenticar el login de un usuario registrado

    mockMvc.perform(post("/api/v1/usuarios/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userExistente)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$ result").value("OK"))
        .andExpect(jsonPath("$ nombre").value("Camilo"))
        .andExpect(jsonPath("$ email").value("camilo@gmail.com"))
        .andExpect(jsonPath("$ password").value("1234"));
    }
    //Test para simular el login de un usuario no registrado
    @Test
    void loginUsuario_ReturnError() throws Exception {
        Usuario userExistente = new Usuario();
        userExistente.setEmail("noexiste@gmail.com");
        userExistente.setPassword("1234");

        //Simular el comportamiento del metodo autenticar con un usuario no registrado
        when(usuarioService.autenticar("noexiste@gmail.com", "1234"))
        .thenReturn(Optional.empty());

        //Simular la peticion POST del usuarioController para iniciar sesion con un usuario no registrado
        mockMvc.perform(post("/api/v1/usuarios/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userExistente)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$ result").value("ERROR"));
    }

}
