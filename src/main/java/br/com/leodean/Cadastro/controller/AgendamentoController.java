package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import br.com.leodean.Cadastro.service.interfaces.IAgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoController {

    @Autowired
    private IAgendamentoService iAgendamentoService;

    @GetMapping()
    private EnvelopData<List<AgendamentoDTO>> listarAgendamentos(Pageable pageable){
        return new EnvelopData<List<AgendamentoDTO>>(iAgendamentoService.listarAgendamentos(pageable));
    }

    @PostMapping()
    private EnvelopData<AgendamentoDTO> createAgendamento(@RequestBody @Valid AgendamentoRequest request){

        return new EnvelopData<AgendamentoDTO>(iAgendamentoService.createAgendamento(request));
    }
}
