package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Customer;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.CustomerDTO;
import br.com.leodean.Cadastro.service.interfaces.ICustomerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Leonardo Angelo
 * @since 25/01/2023
 * *
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final ICustomerService _customerService;

    public CustomerController(ICustomerService _customerService) {
        this._customerService = _customerService;
    }

    @CacheEvict(value = "customer", allEntries = true)
    @PostMapping
    public EnvelopData<CustomerDTO> createCustomer(@RequestBody @Valid Customer request) {
        return new EnvelopData<CustomerDTO>(_customerService.createCustomer(request));
    }

}
