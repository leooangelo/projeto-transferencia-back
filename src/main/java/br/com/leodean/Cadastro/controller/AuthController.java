package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Login;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.service.auth.TokenService;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@RestController
public class AuthController {

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public String login(@RequestBody @Valid Login user){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        Authentication authentication = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        var userResponse = (UsuarioDataBase) authentication.getPrincipal();

        var token =  tokenService.gerarToken(userResponse);
        return token;
    }
}
