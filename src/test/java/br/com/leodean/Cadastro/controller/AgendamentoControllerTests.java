package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.EnumTipoTransacao;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.service.interfaces.IAgendamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AgendamentoControllerTests {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private IAgendamentoService iAgendamentoService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(agendamentoController).build();
    }

    @Test
    public void createAgendamento() throws Exception {
        Mockito.when(iAgendamentoService.createAgendamento(any(AgendamentoRequest.class))).thenReturn(mockAgendamentoDTO());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/agendamento")
                        .content(objectToJson(mockAgendamento()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
    private AgendamentoRequest mockAgendamento() {
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-18");
        return AgendamentoRequest.builder()
                .contaOrigem(mockConta())
                .contaDestino(mockContaDestino())
                .enumTipoTransacao(EnumTipoTransacao.TRANS_TIPO_VALOR)
                .valorTransacao(new BigDecimal(100))
                .valorTotal(new BigDecimal(110))
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .build();
    }
    private AgendamentoDTO mockAgendamentoDTO() {
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-18");
        return AgendamentoDTO.builder()
                .idTransacao(UUID.randomUUID().toString())
                .contaOrigem(mockConta())
                .contaDestino(mockContaDestino())
                .enumTipoTransacao(EnumTipoTransacao.TRANS_TIPO_VALOR.getNome())
                .valorTransacao(new BigDecimal(100))
                .valorTaxa(new BigDecimal(10))
                .valorTotal(new BigDecimal(110))
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .build();
    }
    private ContaDTO mockConta(){
        return ContaDTO.builder()
                .agencia(3747L)
                .numeroConta("256524")
                .account_id(UUID.randomUUID().toString())
                .build();
    }

    private ContaDTO mockContaDestino(){
        return ContaDTO.builder()
                .agencia(3747L)
                .numeroConta("256458")
                .account_id(UUID.randomUUID().toString())
                .build();
    }
    private String objectToJson(AgendamentoRequest agendamento) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(agendamento);
    }
}
