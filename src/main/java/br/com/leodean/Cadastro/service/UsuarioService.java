package br.com.leodean.Cadastro.service;
import br.com.leodean.Cadastro.domain.Customer;
import br.com.leodean.Cadastro.domain.dto.CustomerDTO;
import br.com.leodean.Cadastro.domain.mapper.CustomerMapper;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.ICustomerRepository;
import br.com.leodean.Cadastro.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço que manipula toda informação de customer
 */
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private ICustomerRepository customerRepository;

    /**
     * Metodo que cria um usuário e salva no banco de dados;
     *
     * @param request
     * @return buildCustomer
     */
    @Override
    public CustomerDTO createCustomer(Customer request) {
        try {
            customerExist(request.getEmail(), request.getCell());

            var customerDataBase = CustomerMapper.mappToDataBase(request);

            customerRepository.save(customerDataBase);

            return CustomerMapper.mappToResponse(customerDataBase);
        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "CUSTOMER-01", e.getMessage());
        }
    }

    private void customerExist(String email, String cell) {

        customerRepository.findByEmail(email)
                .ifPresent(check -> {
                    throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "CUSTOMER-06");
                });

        customerRepository.findByCell(cell)
                .ifPresent(check -> {
                    throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "CUSTOMER-07");
                });
    }
}
