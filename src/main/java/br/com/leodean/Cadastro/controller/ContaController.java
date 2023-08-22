package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.service.interfaces.IContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@RestController
@RequestMapping("/api/account")
public class ContaController {

    @Autowired
    private IContaService iAccountService;

    @PostMapping
    public EnvelopData<ContaDTO> createAccout(@RequestBody @Valid Conta request) {

        return new EnvelopData<ContaDTO>(iAccountService.createAccout(request));
    }
}
