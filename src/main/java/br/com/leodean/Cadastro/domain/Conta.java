package br.com.leodean.Cadastro.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conta {

    @NotBlank(message = "cpf nao pode estar vazio")
    @NotNull(message = "cpf é obrigatorio")
    @JsonProperty("cpf")
    private String CPFCorrentista;

    @NotBlank(message = "Agencia nao pode estar vazio")
    @NotNull(message = "Agencia é obrigatorio")
    @JsonProperty("agencia")
    private String agencia;

}
