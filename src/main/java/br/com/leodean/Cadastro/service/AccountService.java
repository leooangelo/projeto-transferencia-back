package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.AccountRequest;
import br.com.leodean.Cadastro.domain.Customer;
import br.com.leodean.Cadastro.domain.databaseDomain.AccountDataBase;
import br.com.leodean.Cadastro.domain.databaseDomain.CustomerDataBase;
import br.com.leodean.Cadastro.domain.dto.AccountDTO;
import br.com.leodean.Cadastro.domain.mapper.AccountMapper;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IAccountRepository;
import br.com.leodean.Cadastro.repositories.ICustomerRepository;
import br.com.leodean.Cadastro.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class AccountService implements IAccountService {

    @Autowired
    private ICustomerRepository iCustomerRepository;
    @Autowired
    private IAccountRepository iAccountRepository;

    @Override
    public AccountDTO createAccout(AccountRequest request) {
        try {
            var customer = iCustomerRepository.findByCPF(request.getCPFCorrentista()).orElseThrow(() -> new ExceptionApiCadastro(HttpStatus.BAD_REQUEST, "CUSTOMER-03"));

            var accountDataBase = AccountMapper.mappToDataBase(request, customer);

            iAccountRepository.save(accountDataBase);

            return AccountMapper.mappToResponse(accountDataBase);

        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "ACCOUNT-01", e.getMessage());
        }
    }
}

