package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;

public interface IAgendamentoService {
    AgendamentoDTO createAgendamento(AgendamentoRequest request);
}
