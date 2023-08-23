package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.databaseDomain.ContaDataBase;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IContaRepository;
import br.com.leodean.Cadastro.repositories.IUsuarioRepository;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ContaServiceTests {

    @InjectMocks
    private ContaService contaService;
    @Mock
    private IUsuarioRepository iUsuarioRepository;
    @Mock
    private IContaRepository iContaRepository;
    @Mock
    private ITokenService tokenService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccount() throws JsonProcessingException {
        when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");

        when(iUsuarioRepository.findByCustomerID("4f50e961-5a28-4af7-8895-7512c4151331")).thenReturn(mockUsuarioDataBase());

        when(iContaRepository.save(any(ContaDataBase.class))).thenReturn(mockContaDataBase());

        var res = contaService.createConta(mockConta());
        assertNotNull(res);
    }


    @Test
    public void createAccoutValidation() throws JsonProcessingException {

        try {
            when(tokenService.getCustomerIdByToken()).thenThrow(new NullPointerException());

             contaService.createConta(mockConta());
        } catch (ExceptionApiCadastro e) {
            assertEquals(e.getCodigoErro(), "ACCOUNT-01");
            assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void createAccoutValidationContaExist() throws JsonProcessingException {

        try {
            when(tokenService.getCustomerIdByToken()).thenReturn("4f50e961-5a28-4af7-8895-7512c4151331");

            when(iUsuarioRepository.findByCustomerID("4f50e961-5a28-4af7-8895-7512c4151331")).thenReturn(mockUsuarioDataBase());

            when(iContaRepository.findByAgenciaAndNumeroConta("3747","256524")).thenReturn(Optional.of(mockContaDataBase()));

            contaService.createConta(mockConta());
        } catch (ExceptionApiCadastro e) {
            assertEquals(e.getCodigoErro(), "ACCOUNT-02");
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }
    private ContaDataBase mockContaDataBase() {
        return ContaDataBase.builder()
                .agencia("3747")
                .numeroConta("256524")
                .CPFCorrentista("24117275003")
                .dataRegistro(LocalDateTime.now())
                .accountId(UUID.randomUUID().toString())
                .build();
    }

    private Conta mockConta() {
        return Conta.builder()
                .agencia("3747")
                .conta("256524")
                .build();
    }

    private UsuarioDataBase mockUsuarioDataBase() {
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
}
