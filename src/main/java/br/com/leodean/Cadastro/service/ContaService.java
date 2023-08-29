package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.databaseDomain.ContaDataBase;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.domain.mapper.ContaMapper;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IContaRepository;
import br.com.leodean.Cadastro.repositories.IUsuarioRepository;
import br.com.leodean.Cadastro.service.interfaces.IContaService;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@Service
public class ContaService implements IContaService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private IContaRepository iContaRepository;
    @Autowired
    private ITokenService tokenService;

    @Override
    public ContaDTO createConta(Conta request) {
        try {
            var customerID = tokenService.getCustomerIdByToken();

            var customer = iUsuarioRepository.findByCustomerID(customerID);
            contaExist(request.getAgencia(), request.getConta());

            var accountDataBase = ContaMapper.mappToDataBase(request, customer);

            iContaRepository.save(accountDataBase);

            return ContaMapper.mappToResponse(accountDataBase);

        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "ACCOUNT-01", e.getMessage());
        }
    }

    @Override
    public List<ContaDTO> listarContas() {

        try {
            var customerID = tokenService.getCustomerIdByToken();

            var listaContaDB = iContaRepository.findAllByIdPessoaCorrentista(customerID);
            List<ContaDTO> listaContas = new ArrayList<>();

            for (ContaDataBase conta : listaContaDB) {
                var accountDTO = ContaMapper.mappToResponse(conta);
                listaContas.add(accountDTO);
            }

            return listaContas;
        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "ACCOUNT-03", e.getMessage());
        }
    }

    private void contaExist(String agencia, String accountId) {

        iContaRepository.findByAgenciaAndNumeroConta(agencia, accountId)
                .ifPresent(check -> {
                    throw new ExceptionApiCadastro(HttpStatus.BAD_REQUEST, "ACCOUNT-02");
                });
    }
}

