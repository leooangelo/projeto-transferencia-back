package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Login;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @InjectMocks
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ITokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void login() throws Exception {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(mockUsuarioDB(), "LeonardoTeste", authorities);

        var rr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW96bmNvbnRhdG9AZ21haWwuY29tIiwiaXNzIjoiQ2FkYXN0cm8iLCJpZCI6IjA0Y2UzMzk2LThhNTUtNGNkZC1hYzBjLWU1NTI5MDEyMzliNiIsImV4cCI6MTY5Mjc0NzU2OH0.Bjv2eSKe6zkuoMOxYf0sdLX0N4gSd_oKnfKPiEW-D_Y";
        Mockito.when(tokenService.gerarToken(any(UsuarioDataBase.class))).thenReturn(rr);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(usernamePasswordAuthenticationToken);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .content(objectToJson(mockLogin()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    private String objectToJson(Login login) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(login);
    }

    private Login mockLogin(){
        return Login.builder()
                .email("leozncontato@gmail.com")
                .password("LeonardoTeste")
                .build();
    }
    private UsuarioDataBase mockUsuarioDB(){
        return UsuarioDataBase.builder()
                .name("Leonardo Angelo")
                .cell("11916691211")
                .email("leozncontato@gmail.com")
                .CPF("46617438817")
                .password("LeonardoTeste")
                .customerID("30a53662-1f9d-41e5-b435-b24e4c164a45")
                .dataRegistro(LocalDateTime.now())
                .build();
    }
}
