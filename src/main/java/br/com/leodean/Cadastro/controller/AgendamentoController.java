package br.com.leodean.Cadastro.controller;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.data.EnvelopData;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import br.com.leodean.Cadastro.service.interfaces.IAgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoController {

    @Autowired
    private IAgendamentoService iAgendamentoService;

    @PostMapping()
    private EnvelopData<AgendamentoDTO> createAgendamento(@RequestBody @Valid AgendamentoRequest request){

        return new EnvelopData<AgendamentoDTO>(iAgendamentoService.createAgendamento(request));
    }
}
