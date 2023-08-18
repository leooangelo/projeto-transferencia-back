package br.com.leodean.Cadastro.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {

    @JsonProperty("id_conta")
    private String account_id;
    @JsonProperty("agencia")
    private Long agencia;
    @JsonProperty("numero_conta")
    private String numeroConta;

}
