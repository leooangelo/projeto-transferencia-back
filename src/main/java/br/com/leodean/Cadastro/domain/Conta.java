package br.com.leodean.Cadastro.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conta {

    @NotBlank(message = "Conta não pode estar vazio")
    @NotNull(message = "Conta é obrigatório")
    @JsonProperty("conta")
    @Size(min = 6, max = 6, message
            = "Conta deve conter 6 digitos")
    private String conta;

    @NotBlank(message = "Agência não pode estar vazio")
    @NotNull(message = "Agência é obrigatório")
    @Size(min = 4, max = 4, message
            = "Agência deve conter 6 digitos")
    @JsonProperty("agencia")
    private String agencia;

}
