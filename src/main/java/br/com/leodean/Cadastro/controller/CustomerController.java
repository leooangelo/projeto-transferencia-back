package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.Customer;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.CustomerDTO;
import br.com.leodean.Cadastro.service.interfaces.ICustomerService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
