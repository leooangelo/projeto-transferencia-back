package br.com.leodean.Cadastro.service;
import br.com.leodean.Cadastro.domain.Usuario;
import br.com.leodean.Cadastro.domain.dto.UsuarioDTO;
import br.com.leodean.Cadastro.domain.mapper.UsuarioMapper;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IUsuarioRepository;
import br.com.leodean.Cadastro.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 * Classe de serviço que manipula toda informação de customer
 */
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository customerRepository;

    /**
     * Metodo que cria um usuário e salva no banco de dados;
     *
     * @param request
     * @return buildCustomer
     */
    @Override
    public UsuarioDTO createCustomer(Usuario request) {
        try {
            customerExist(request.getEmail(), request.getCell(), request.getCPF());

            var customerDataBase = UsuarioMapper.mappToDataBase(request);

            customerRepository.save(customerDataBase);

            return UsuarioMapper.mappToResponse(customerDataBase);
        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "CUSTOMER-01", e.getMessage());
        }
    }

    private void customerExist(String email, String cell, String cpf) {

        customerRepository.findByEmail(email)
                .ifPresent(check -> {
                    throw new ExceptionApiCadastro(HttpStatus.BAD_REQUEST, "CUSTOMER-06");
                });

        customerRepository.findByCell(cell)
                .ifPresent(check -> {
                    throw new ExceptionApiCadastro(HttpStatus.BAD_REQUEST, "CUSTOMER-07");
                });

        customerRepository.findByCPF(cpf)
                .ifPresent(check -> {
                    throw new ExceptionApiCadastro(HttpStatus.BAD_REQUEST, "CUSTOMER-08");
                });
    }
}
