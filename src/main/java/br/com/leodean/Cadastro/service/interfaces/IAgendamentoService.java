package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IAgendamentoService {
    AgendamentoDTO createAgendamento(AgendamentoRequest request);

    List<AgendamentoDTO> listarAgendamentos(Pageable pageable);
}
