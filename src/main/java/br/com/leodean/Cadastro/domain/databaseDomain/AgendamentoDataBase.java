package br.com.leodean.Cadastro.domain.databaseDomain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_agendamentos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDataBase {

    @Id
    @Column(name = "id_transacao", nullable = false)
    private String idTransacao;

    private String agenciaOrigem;

    private String contaOrigem;

    private String agenciaDestino;

    private String contaDestino;

    private String tipoTransacao;

    private BigDecimal valorTransacao;

    private BigDecimal valorTaxa;

    private BigDecimal valorTotal;

    private LocalDate dataTransacao;

    private LocalDate dataAgendamento;

    private LocalDateTime dataRegistro;
}
