package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.AccountRequest;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.AccountDTO;
import br.com.leodean.Cadastro.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private IAccountService iAccountService;

    @PostMapping
    public EnvelopData<AccountDTO> createAccout(@RequestBody @Valid AccountRequest request){

        return new EnvelopData<AccountDTO>(iAccountService.createAccout(request));
    }
}
