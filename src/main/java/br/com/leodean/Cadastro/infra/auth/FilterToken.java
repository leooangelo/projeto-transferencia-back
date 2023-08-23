package br.com.leodean.Cadastro.infra.auth;

import br.com.leodean.Cadastro.repositories.IUsuarioRepository;
import br.com.leodean.Cadastro.service.auth.TokenService;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private ITokenService tokenService;


    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token;

        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null){

            token = authorizationHeader.replace("Bearer ", "");

            var subject = this.tokenService.getSubject(token);

            var usuario = this.usuarioRepository.findByEmailToken(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
