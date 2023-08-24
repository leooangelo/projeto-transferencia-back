package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;

import java.util.List;

public interface IContaService {
    ContaDTO createConta(Conta request);

    List<ContaDTO> listarContas();
}
