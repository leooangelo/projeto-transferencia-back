package br.com.leodean.Cadastro.domain.databaseDomain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_Account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaDataBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_conta", nullable = false)
    private String accountId;

    @Column(name = "cpf_correntista", nullable = false)
    private String CPFCorrentista;

    @Column(name = "id_pessoa_correntista", nullable = false)
    private String idPessoaCorrentista;

    @Column(name = "agencia", nullable = false)
    private String agencia;

    @Column(name = "codigo_conta", nullable = false)
    private String numeroConta;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

}
