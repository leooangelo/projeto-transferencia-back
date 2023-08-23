package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.service.interfaces.IContaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContaControllerTests {

    @InjectMocks
    private ContaController contaController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IContaService iContaService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(contaController).build();
    }

    @Test
    public void createCustomer() throws Exception {
        Mockito.when(iContaService.createAccout(any(Conta.class))).thenReturn(mockContaDTO());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/account")
                        .content(objectToJson(mockConta()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    private String objectToJson(Conta conta) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(conta);
    }
    private ContaDTO mockContaDTO(){
        return ContaDTO.builder()
                .agencia(3747L)
                .numeroConta("256524")
                .account_id(UUID.randomUUID().toString())
                .build();
    }
    private Conta mockConta(){
        return Conta.builder()
                .agencia("3747")
                .conta("256524")
                .build();
    }
}
