package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Usuario;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.UsuarioDTO;
import br.com.leodean.Cadastro.service.interfaces.IUsuarioService;
import org.springframework.cache.annotation.CacheEvict;
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
@RequestMapping("/api/customer")
public class UsuarioController {

    private final IUsuarioService _customerService;

    public UsuarioController(IUsuarioService _customerService) {
        this._customerService = _customerService;
    }

    @CacheEvict(value = "customer", allEntries = true)
    @PostMapping
    public EnvelopData<UsuarioDTO> createCustomer(@RequestBody @Valid Usuario request) {
        return new EnvelopData<UsuarioDTO>(_customerService.createCustomer(request));
    }

}
