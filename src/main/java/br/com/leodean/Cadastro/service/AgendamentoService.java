package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.EnumTipoTransacao;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import br.com.leodean.Cadastro.domain.mapper.AgendamentoMapper;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IAgendamentoRepository;
import br.com.leodean.Cadastro.service.interfaces.IAgendamentoService;
import br.com.leodean.Cadastro.utils.ICalculaValorTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class AgendamentoService implements IAgendamentoService {

    @Autowired
    private ICalculaValorTransferencia iCalculaValorTransferencia;
    @Autowired
    private IAgendamentoRepository iAgendamentoRepository;
    @Override
    public AgendamentoDTO createAgendamento(AgendamentoRequest request) {

        try{
            var taxaTransacao = tipoTaxaTrasacao(request);
            AgendamentoMapper.mappTaxaToObject(taxaTransacao, request);

            var agendamentoDataBase = AgendamentoMapper.mappToDataBase(request,taxaTransacao);

            iAgendamentoRepository.save(agendamentoDataBase);

            return AgendamentoMapper.mappToResponse(agendamentoDataBase.getIdTransacao(),request,taxaTransacao);
        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "AGENDAMENTO-01", e.getMessage());
        }
    }


    private BigDecimal tipoTaxaTrasacao(AgendamentoRequest request){
        var diferencaData = verificaData(request.getDataTransacao(), request.getDataAgendamento());

        if(request.getEnumTipoTransacao().getNome().equals(EnumTipoTransacao.TRANS_DIA.getNome()) && diferencaData == 0)
        {
            return iCalculaValorTransferencia.caculoValorTransferenciaD0(request.getValorTransacao());
        }

        else if(request.getEnumTipoTransacao().getNome().equals(EnumTipoTransacao.TRANS_DIA_10.getNome()) && diferencaData <= 10)
        {
            return iCalculaValorTransferencia.calculaValorTransferenciaD10(request.getValorTransacao());
        }

        else if(request.getEnumTipoTransacao().getNome().equals(EnumTipoTransacao.TRANS_RESGRESSIVA.getNome()) && diferencaData > 10)
        {
            return iCalculaValorTransferencia.calculaValorTransferenciaRegressiva(diferencaData,request.getValorTransacao());
        }

        return iCalculaValorTransferencia.caculaValorTransferenciaTipoValor(diferencaData,request.getValorTransacao());

    }


    private long verificaData(LocalDate dataTransacao, LocalDate dataAgendamento) {
        var diferencaData = dataAgendamento.until(dataTransacao, ChronoUnit.DAYS);
        return diferencaData;
    }
}
