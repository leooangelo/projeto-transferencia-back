package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.EnumTipoTransacao;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.service.interfaces.IAgendamentoService;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AgendamentoControllerTests {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private IAgendamentoService iAgendamentoService;

    @Mock
    private ITokenService tokenService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc =MockMvcBuilders.standaloneSetup(agendamentoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
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

    @Test
    public void listarAgendamentos() throws Exception {

        when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");
        Mockito.when(iAgendamentoService.listarAgendamentos(any())).thenReturn(mockAgendamentoPage());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/agendamento")
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

    private Page<AgendamentoDTO> mockAgendamentoPage() {
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-18");
        var res =  AgendamentoDTO.builder()
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
        return new PageImpl<>(Arrays.asList(res));
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
