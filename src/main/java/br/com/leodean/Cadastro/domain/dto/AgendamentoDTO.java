package br.com.leodean.Cadastro.domain.dto;

import br.com.leodean.Cadastro.domain.EnumTipoTransacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AgendamentoDTO {

    @JsonProperty("id_transacao")
    private String idTransacao;

    @JsonProperty("conta_origem")
    private AccountDTO contaOrigem;

    @JsonProperty("conta_destino")
    private AccountDTO contaDestino;

    @JsonProperty("tipo_transacao")
    private String enumTipoTransacao;

    @JsonProperty("valor")
    private BigDecimal valorTransacao;

    @JsonProperty("valor_taxa")
    private BigDecimal valorTaxa;

    @JsonProperty("valor_total")
    private BigDecimal valorTotal;

    @JsonProperty("data_transacao")
    private LocalDate dataTransacao;

    @JsonProperty("data_agendamento")
    private LocalDate dataAgendamento;
}
