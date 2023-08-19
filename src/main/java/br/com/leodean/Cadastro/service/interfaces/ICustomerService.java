package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.Customer;
import br.com.leodean.Cadastro.domain.dto.CustomerDTO;

public interface ICustomerService {
    CustomerDTO createCustomer(Customer request);
}
