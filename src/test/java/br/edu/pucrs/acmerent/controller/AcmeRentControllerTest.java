package br.edu.pucrs.acmerent.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AcmeRentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testListaAutomoveis() throws Exception {
        mockMvc.perform(get("/acmerent/listaautomoveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void testListaClientes() throws Exception {
        mockMvc.perform(get("/acmerent/listaclientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").exists());
    }

    @Test
    void testListaLocacoes() throws Exception {
        mockMvc.perform(get("/acmerent/listalocacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numero").exists());
    }

    @Test
    void testConsultaCliente() throws Exception {
        mockMvc.perform(get("/acmerent/consultacliente?codigo=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(1));
    }

    @Test
    void testValidaAutomovel() throws Exception {
        mockMvc.perform(post("/acmerent/validaautomovel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":4}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testCadastraLocacao() throws Exception {
        mockMvc.perform(post("/acmerent/atendimento/cadlocacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"numero\": 99, " +
                        "\"clienteId\": 1, " +
                        "\"automovelId\": 3, " +
                        "\"dias\": 4, " +
                        "\"datainicial\": \"2025-05-03\", " +
                        "\"valordiaria\": 100.00}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testAtualizaAutomovel() throws Exception {
        mockMvc.perform(post("/acmerent/atendimento/atualizaautomovel/1/estado/RENTED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RENTED"));
    }
} 