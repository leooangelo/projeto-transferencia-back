package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.domain.mapper.ContaMapper;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IContaRepository;
import br.com.leodean.Cadastro.repositories.IUsuarioRepository;
import br.com.leodean.Cadastro.service.interfaces.IContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    @Override
    public ContaDTO createAccout(Conta request) {
        try {
            var customer = iUsuarioRepository.findByCPF(request.getCPFCorrentista()).orElseThrow(() -> new ExceptionApiCadastro(HttpStatus.BAD_REQUEST, "CUSTOMER-03"));

            var accountDataBase = ContaMapper.mappToDataBase(request, customer);

            iContaRepository.save(accountDataBase);

            return ContaMapper.mappToResponse(accountDataBase);

        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "ACCOUNT-01", e.getMessage());
        }
    }
}

