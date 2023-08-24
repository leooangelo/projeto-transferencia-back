package br.com.leodean.Cadastro.service.interfaces.auth;

import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ITokenService {
    String gerarToken(UsuarioDataBase userResponse);

    String getSubject(String token);

    String getCustomerIdByToken() throws JsonProcessingException;
}
