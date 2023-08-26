package br.com.leodean.Cadastro.domain.mapper;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.databaseDomain.AgendamentoDataBase;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@Component
public class AgendamentoMapper {

    public static AgendamentoDataBase mappToDataBase(AgendamentoRequest request, BigDecimal taxaTransacao, String customerID){
        return AgendamentoDataBase.builder()
                .idTransacao(UUID.randomUUID().toString())
                .idPessoaOrigem(customerID)
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

    public static AgendamentoDTO mappToResponse(Long id,String idTransacao,AgendamentoRequest request, BigDecimal valorTaxa) {
        return AgendamentoDTO.builder()
                .id(id)
                .idTransacao(idTransacao)
                .contaOrigem(request.getContaOrigem())
                .contaDestino(request.getContaDestino())
                .enumTipoTransacao(request.getEnumTipoTransacao().getNome())
                .valorTransacao(request.getValorTransacao())
                .dataAgendamento(request.getDataAgendamento())
                .dataTransacao(request.getDataTransacao())
                .valorTaxa(valorTaxa)
                .valorTotal(request.getValorTotal())
                .build();
    }
    public static AgendamentoDTO mappToResponse(AgendamentoDataBase agendamentoDataBase) {
        return AgendamentoDTO.builder()
                .id(agendamentoDataBase.getId())
                .idTransacao(agendamentoDataBase.getIdTransacao())
                .contaOrigem(ContaDTO.builder()
                        .numeroConta(agendamentoDataBase.getContaOrigem())
                        .agencia(Long.parseLong(agendamentoDataBase.getAgenciaOrigem()))
                        .build())
                .contaDestino(ContaDTO.builder()
                        .numeroConta(agendamentoDataBase.getContaDestino())
                        .agencia(Long.parseLong(agendamentoDataBase.getAgenciaDestino()))
                        .build())
                .enumTipoTransacao(agendamentoDataBase.getTipoTransacao())
                .valorTransacao(agendamentoDataBase.getValorTransacao())
                .dataAgendamento(agendamentoDataBase.getDataAgendamento())
                .dataTransacao(agendamentoDataBase.getDataTransacao())
                .valorTaxa(agendamentoDataBase.getValorTaxa())
                .valorTotal(agendamentoDataBase.getValorTotal())
                .build();
    }
}
