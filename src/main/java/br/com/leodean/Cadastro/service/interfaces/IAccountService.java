package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.AccountRequest;
import br.com.leodean.Cadastro.domain.dto.AccountDTO;

public interface IAccountService {
    AccountDTO createAccout(AccountRequest request);
}
