package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.Usuario;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.domain.dto.UsuarioDTO;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IUsuarioRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceTests {

    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private IUsuarioRepository customerRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createCustomer(){

        when(customerRepository.save(any(UsuarioDataBase.class))).thenReturn(mockUsuarioDataBase().get());
        var response = usuarioService.createCustomer(mockUsuario());
        assertNotNull(response);
    }

    @Test
    public void createCustomerValidation(){

        try {
            when(customerRepository.save(any(UsuarioDataBase.class))).thenThrow(new NullPointerException());
            usuarioService.createCustomer(mockUsuario());
        }catch (ExceptionApiCadastro e){
            assertEquals(e.getCodigoErro(), "CUSTOMER-01");
            assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void createCustomerValidationEmail(){
        try {
            when(customerRepository.findByEmail("leozncon@gmail.com")).thenReturn(mockUsuarioDataBase());
            usuarioService.createCustomer(mockUsuario());
        }catch (ExceptionApiCadastro e){
            assertEquals(e.getCodigoErro(), "CUSTOMER-06");
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    public void createCustomerValidationCell(){
        try {
            when(customerRepository.findByCell("11916691713")).thenReturn(mockUsuarioDataBase());
            usuarioService.createCustomer(mockUsuario());
        }catch (ExceptionApiCadastro e){
            assertEquals(e.getCodigoErro(), "CUSTOMER-07");
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    private Optional<UsuarioDataBase> mockUsuarioDataBase(){
        var res = UsuarioDataBase.builder()
                .name("Leonardo Angelo")
                .cell("11916691713")
                .email("leozncon@gmail.com")
                .CPF("24117275003")
                .password("LeonardoTeste")
                .customerID("4f50e961-5a28-4af7-8895-7512c4151331")
                .dataRegistro(LocalDateTime.now())
                .build();

        return Optional.of(res);
    }
    private Usuario mockUsuario(){
        return Usuario.builder()
                .name("Leonardo Angelo")
                .cell("11916691713")
                .email("leozncon@gmail.com")
                .CPF("24117275003")
                .password("LeonardoTeste")
                .build();
    }
    private UsuarioDTO mockUsuarioDTO(){
        return UsuarioDTO.builder()
                .customerID("4f50e961-5a28-4af7-8895-7512c4151331")
                .name("Leonardo Angelo")
                .cell("11916691713")
                .email("leozncon@gmail.com")
                .build();
    }
}
