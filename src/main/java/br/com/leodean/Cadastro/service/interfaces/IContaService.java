package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;

public interface IContaService {
    ContaDTO createAccout(Conta request);
}
