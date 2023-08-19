package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.AccountRequest;
import br.com.leodean.Cadastro.domain.dto.AccountDTO;

public interface IContaService {
    AccountDTO createAccout(AccountRequest request);
}
