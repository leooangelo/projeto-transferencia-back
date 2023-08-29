package br.com.leodean.Cadastro.domain.databaseDomain;

import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_transacao", nullable = false)
    private String idTransacao;

    @Column(name = "id_pessoa_origem", nullable = false)
    private String idPessoaOrigem;

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
