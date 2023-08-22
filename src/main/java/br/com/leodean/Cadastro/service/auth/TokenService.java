package br.com.leodean.Cadastro.service.auth;


import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.infra.auth.TokenDecode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
public class TokenService {

    @Autowired
    private HttpServletRequest request;

    public String gerarToken(UsuarioDataBase userResponse) {

        return JWT.create()
                .withIssuer("Cadastro")
                .withSubject(userResponse.getEmail())
                .withClaim("id", userResponse.getCustomerID())
                .withExpiresAt(LocalDateTime.now().plusMinutes(30)
                        .toInstant(ZoneOffset.of("-03:00"))

                ).sign(Algorithm.HMAC256("secreta"));
    }

    public String getSubject(String token) {

        return JWT.require(Algorithm.HMAC256("secreta"))
                .withIssuer("Cadastro")
                .build().verify(token).getSubject();
    }

    public String getCustomerIdByToken() throws JsonProcessingException {
        var tokenFormat = request.getHeader("authorization").replace("Bearer ","");
        var token = tokenFormat.split("\\.");

        String base64EncodedHeader = token[0];
        String base64EncodedBody = token[1];
        String base64EncodedSignature = token[2];
        String body = new String(Base64.getDecoder().decode(base64EncodedBody));
        ObjectMapper objectMapper = new ObjectMapper();
        TokenDecode tokenDecodeObject = objectMapper.readValue(body, TokenDecode.class);

        return tokenDecodeObject.getId();
    }

}
