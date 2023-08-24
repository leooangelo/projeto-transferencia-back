package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAgendamentoService {
    AgendamentoDTO createAgendamento(AgendamentoRequest request);

    Page<AgendamentoDTO> listarAgendamentos(Pageable pageable);
}
