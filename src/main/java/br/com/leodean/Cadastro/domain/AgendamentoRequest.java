package br.com.leodean.Cadastro.domain;

import br.com.leodean.Cadastro.domain.dto.AccountDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Valid
public class AgendamentoRequest {

    @NotNull(message = "Conta Origem é obrigatorio")
    @JsonProperty("conta_origem")
    private AccountDTO contaOrigem;

    @NotNull(message = "Conta Destino é obrigatorio")
    @JsonProperty("conta_destino")
    private AccountDTO contaDestino;

    @NotNull(message = "Tipo Transacao é obrigatorio")
    @JsonProperty("tipo_transacao")
    private EnumTipoTransacao enumTipoTransacao;

    @NotNull(message = "Valor é obrigatorio")
    @JsonProperty("valor")
    private BigDecimal valorTransacao;

    private BigDecimal valorTotal;

    @JsonProperty("data_transacao")
    private LocalDate dataTransacao;

    @NotNull(message = "Data Agendamento  é obrigatorio")
    @JsonProperty("data_agendamento")
    private LocalDate dataAgendamento;

}
