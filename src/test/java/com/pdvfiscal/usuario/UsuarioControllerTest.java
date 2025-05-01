package com.pdvfiscal.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdvfiscal.controller.UsuarioController;
import com.pdvfiscal.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCadastroELoginUsuario() throws Exception {
        // Cadastro
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Usuário");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("123456");
        usuario.setPerfil("ADMIN");

        mockMvc.perform(post("/api/usuarios/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        // Login
        UsuarioController.LoginRequest login = new UsuarioController.LoginRequest();
        login.setEmail("teste@teste.com");
        login.setSenha("123456");

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("teste@teste.com"));
    }
}
