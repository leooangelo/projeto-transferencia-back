package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Usuario;
import br.com.leodean.Cadastro.domain.dto.UsuarioDTO;
import br.com.leodean.Cadastro.service.interfaces.IUsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class UsuarioControllerTests {

    @InjectMocks
    private UsuarioController usuarioController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUsuarioService iUsuarioService;

   // private AutoCloseable autoCloseable;

//    @BeforeEach
//    public void before(){
//        autoCloseable = MockitoAnnotations.openMocks(this);
//    }
//
//    @AfterEach
//    public void releaseMock() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    public void createCustomer() throws Exception {
        Mockito.when(iUsuarioService.createCustomer(any(Usuario.class))).thenReturn(mockUsuarioDTO());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/customer")
                .content(objectToJson(mockUsuario()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    private String objectToJson(Usuario usuario) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(usuario);
    }

    private Usuario mockUsuario(){
        return Usuario.builder()
                .name("Leonardo Angelo")
                .cell("11916691211")
                .email("leozncontato@gmail.com")
                .CPF("46617438814")
                .password("LeonardoTeste")
                .build();
    }
    private UsuarioDTO mockUsuarioDTO(){
        return UsuarioDTO.builder()
                .customerID("4f50e961-5a28-4af7-8895-7512c4151331")
                .name("Leonardo Angelo")
                .cell("11916691211")
                .email("leozncontato@gmail.com")
                .build();
    }
}