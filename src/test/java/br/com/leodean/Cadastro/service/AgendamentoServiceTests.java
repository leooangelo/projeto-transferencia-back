package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.EnumTipoTransacao;
import br.com.leodean.Cadastro.domain.databaseDomain.AgendamentoDataBase;
import br.com.leodean.Cadastro.domain.databaseDomain.ContaDataBase;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private UsuarioDataBase mockUsuarioDataBase(){
        return UsuarioDataBase.builder()
                .name("Leonardo Angelo")
                .cell("11916691713")
                .email("leozncon@gmail.com")
                .CPF("24117275003")
                .password("LeonardoTeste")
                .customerID("4f50e961-5a28-4af7-8895-7512c4151331")
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
