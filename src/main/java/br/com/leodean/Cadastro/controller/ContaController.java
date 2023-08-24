package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.service.interfaces.IContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

        return new EnvelopData<ContaDTO>(iAccountService.createConta(request));
    }

    @GetMapping
    public EnvelopData<List<ContaDTO>> listarContas(){
        return new EnvelopData<List<ContaDTO>>(iAccountService.listarContas());
    }
}
