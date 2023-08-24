package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.EnumTipoTransacao;
import br.com.leodean.Cadastro.domain.databaseDomain.AgendamentoDataBase;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IAgendamentoRepository;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
import br.com.leodean.Cadastro.utils.interfaces.ICalculaValorTransferencia;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AgendamentoServiceTests {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private ICalculaValorTransferencia iCalculaValorTransferencia;
    @Mock
    private IAgendamentoRepository iAgendamentoRepository;
    @Mock
    private ITokenService tokenService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void listarAgendamentos() throws JsonProcessingException {
        Pageable request = PageRequest.of(0, 2);
        when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");
        when(iAgendamentoRepository.findByIdPessoaOrigem(request,"4f50e961-5a28-4af7-8895-7512c4151331"))
                .thenReturn(mockAgendamento());

        var response = agendamentoService.listarAgendamentos(request);
        assertNotNull(response);
        assertNotNull(response.getContent());
    }

    @Test
    public void listarAgendamentosError() throws JsonProcessingException {
        try {
            Pageable request = PageRequest.of(0, 2);
            when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");
            when(iAgendamentoRepository.findByIdPessoaOrigem(request,"4f50e961-5a28-4af7-8895-7512c4151331"))
                    .thenThrow(new NullPointerException());
            var response = agendamentoService.listarAgendamentos(request);
        }catch (ExceptionApiCadastro e ){
            assertEquals(e.getCodigoErro(),"AGENDAMENTO-01");
            assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void createAgendamento() throws JsonProcessingException {

        when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");
        when(iCalculaValorTransferencia.caculoValorTransferenciaD0(new BigDecimal("10"))).thenReturn(new BigDecimal("3.33"));
        when(iAgendamentoRepository.save(any(AgendamentoDataBase.class))).thenReturn(mockAgendamentoDB());
        var response = agendamentoService.createAgendamento(mockAgendamentoRequest());
        assertNotNull(response);
    }

    @Test
    public void createAgendamentoD10() throws JsonProcessingException {

        when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");
        when(iCalculaValorTransferencia.calculaValorTransferenciaD10(new BigDecimal("10"))).thenReturn(new BigDecimal("3.33"));
        when(iAgendamentoRepository.save(any(AgendamentoDataBase.class))).thenReturn(mockAgendamentoDBD10());
        var response = agendamentoService.createAgendamento(mockAgendamentoRequestD10());
        assertNotNull(response);
    }

    @Test
    public void createAgendamentoRegressiva() throws JsonProcessingException {

        when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");
        when(iCalculaValorTransferencia.calculaValorTransferenciaRegressiva(20l,new BigDecimal("10"))).thenReturn(new BigDecimal("3.33"));
        when(iAgendamentoRepository.save(any(AgendamentoDataBase.class))).thenReturn(mockAgendamentoDBDRegressiva());
        var response = agendamentoService.createAgendamento(mockAgendamentoRequestRegressiva());
        assertNotNull(response);
    }

    @Test
    public void createAgendamentoRegressivaErro() throws JsonProcessingException {

        try {
            when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");
            when(iCalculaValorTransferencia.calculaValorTransferenciaRegressiva(20l,new BigDecimal("10"))).thenReturn(new BigDecimal("3.33"));
            when(iAgendamentoRepository.save(any(AgendamentoDataBase.class))).thenThrow(new NullPointerException ());
            var response = agendamentoService.createAgendamento(mockAgendamentoRequestRegressiva());
        }catch (ExceptionApiCadastro e ){
            assertEquals(e.getStatus(),HttpStatus.INTERNAL_SERVER_ERROR);
            assertEquals(e.getCodigoErro(), "AGENDAMENTO-01");
        }

    }

    private AgendamentoRequest mockAgendamentoRequestRegressiva(){
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-09-07");
        return AgendamentoRequest.builder()
                .contaOrigem(mockConta())
                .contaDestino(mockContaDestino())
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .enumTipoTransacao(EnumTipoTransacao.TRANS_RESGRESSIVA)
                .valorTransacao(new BigDecimal("10"))
                .build();

    }

    private AgendamentoDataBase mockAgendamentoDBDRegressiva() {
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-09-07");
        return AgendamentoDataBase.builder()
                .idTransacao(UUID.randomUUID().toString())
                .contaOrigem("256524")
                .contaDestino("256458")
                .tipoTransacao(EnumTipoTransacao.TRANS_RESGRESSIVA.getNome())
                .valorTransacao(new BigDecimal(100))
                .valorTaxa(new BigDecimal(10))
                .valorTotal(new BigDecimal(110))
                .idPessoaOrigem("4f50e961-5a28-4af7-8895-7512c4151331")
                .agenciaOrigem("3747")
                .agenciaDestino("3747")
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .dataRegistro(LocalDateTime.now())
                .build();
    }
    private AgendamentoRequest mockAgendamentoRequestD10(){
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-27");
        return AgendamentoRequest.builder()
                .contaOrigem(mockConta())
                .contaDestino(mockContaDestino())
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .enumTipoTransacao(EnumTipoTransacao.TRANS_DIA_10)
                .valorTransacao(new BigDecimal("10"))
                .build();
    }

    private AgendamentoDataBase mockAgendamentoDBD10() {
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-27");
        return AgendamentoDataBase.builder()
                .idTransacao(UUID.randomUUID().toString())
                .contaOrigem("256524")
                .contaDestino("256458")
                .tipoTransacao(EnumTipoTransacao.TRANS_DIA_10.getNome())
                .valorTransacao(new BigDecimal(100))
                .valorTaxa(new BigDecimal(10))
                .valorTotal(new BigDecimal(110))
                .idPessoaOrigem("4f50e961-5a28-4af7-8895-7512c4151331")
                .agenciaOrigem("3747")
                .agenciaDestino("3747")
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    private AgendamentoRequest mockAgendamentoRequest(){
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-18");
        return AgendamentoRequest.builder()
                .contaOrigem(mockConta())
                .contaDestino(mockContaDestino())
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .enumTipoTransacao(EnumTipoTransacao.TRANS_DIA)
                .valorTransacao(new BigDecimal("10"))
                .build();
    }
    private AgendamentoDataBase mockAgendamentoDB() {
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-18");
        return AgendamentoDataBase.builder()
                .idTransacao(UUID.randomUUID().toString())
                .contaOrigem("256524")
                .contaDestino("256458")
                .tipoTransacao(EnumTipoTransacao.TRANS_TIPO_VALOR.getNome())
                .valorTransacao(new BigDecimal(100))
                .valorTaxa(new BigDecimal(10))
                .valorTotal(new BigDecimal(110))
                .idPessoaOrigem("4f50e961-5a28-4af7-8895-7512c4151331")
                .agenciaOrigem("3747")
                .agenciaDestino("3747")
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .dataRegistro(LocalDateTime.now())
                .build();
    }


    private Page<AgendamentoDataBase> mockAgendamento() {
        var dataAgendamento = LocalDate.parse("2018-08-18");
        var dataTransacao = LocalDate.parse("2018-08-18");
        var res = AgendamentoDataBase.builder()
                .idTransacao(UUID.randomUUID().toString())
                .contaOrigem("256524")
                .contaDestino("256458")
                .tipoTransacao(EnumTipoTransacao.TRANS_TIPO_VALOR.getNome())
                .valorTransacao(new BigDecimal(100))
                .valorTaxa(new BigDecimal(10))
                .valorTotal(new BigDecimal(110))
                .idPessoaOrigem("4f50e961-5a28-4af7-8895-7512c4151331")
                .agenciaOrigem("3747")
                .agenciaDestino("3747")
                .dataAgendamento(dataAgendamento)
                .dataTransacao(dataTransacao)
                .dataRegistro(LocalDateTime.now())
                .build();
        return new PageImpl<>(Arrays.asList(res));
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

}
