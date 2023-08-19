package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.Customer;
import br.com.leodean.Cadastro.domain.dto.CustomerDTO;

public interface IUsuarioService {
    CustomerDTO createCustomer(Customer request);
}
