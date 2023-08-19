package br.com.leodean.Cadastro.domain.mapper;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.databaseDomain.AgendamentoDataBase;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AgendamentoMapper {

    public static AgendamentoDataBase mappToDataBase(AgendamentoRequest request, BigDecimal taxaTransacao){
        return AgendamentoDataBase.builder()
                .idTransacao(UUID.randomUUID().toString())
                .agenciaOrigem(request.getContaOrigem().getAgencia().toString())
                .contaOrigem(request.getContaOrigem().getNumeroConta())
                .agenciaDestino(request.getContaDestino().getAgencia().toString())
                .contaDestino(request.getContaDestino().getNumeroConta())
                .tipoTransacao(request.getEnumTipoTransacao().getNome())
                .valorTransacao(request.getValorTransacao())
                .valorTaxa(taxaTransacao)
                .valorTotal(request.getValorTotal())
                .dataTransacao(request.getDataTransacao())
                .dataAgendamento(request.getDataAgendamento())
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public static void mappTaxaToObject(BigDecimal taxaTransacao, AgendamentoRequest request) {
        request.setValorTotal(taxaTransacao.add(request.getValorTransacao()));
    }

    public static AgendamentoDTO mappToResponse(String idTransacao,AgendamentoRequest request, BigDecimal valorTaxa) {
        return AgendamentoDTO.builder()
                .idTransacao(idTransacao)
                .contaOrigem(request.getContaOrigem())
                .contaDestino(request.getContaDestino())
                .enumTipoTransacao(request.getEnumTipoTransacao())
                .valorTransacao(request.getValorTransacao())
                .dataAgendamento(request.getDataAgendamento())
                .dataTransacao(request.getDataTransacao())
                .valorTaxa(valorTaxa)
                .valorTotal(request.getValorTotal())
                .build();
    }
}
