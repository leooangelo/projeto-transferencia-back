package br.com.leodean.Cadastro.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Valid
public class Usuario {

    @JsonProperty("id_cliente")
    private String customerID;

    @NotBlank(message = "Nome nao pode estar vazio")
    @NotNull(message = "Nome é obrigatorio")
    @JsonProperty("nome")
    private String name;

    @NotBlank(message = "Telefone nao pode estar vazio")
    @NotNull(message = "Telefone é obrigatorio")
    @JsonProperty("telefone")
    @Size(min = 11, max = 11, message
            = "Telefone deve conter 11 digitos")
    private String cell;

    @NotBlank(message = "Email nao pode estar vazio")
    @NotNull(message = "Email é obrigatorio")
    @JsonProperty("email")
    @Email
    private String email;

    @NotBlank(message = "Senha nao pode estar vazio")
    @NotNull(message = "Senha é obrigatorio")
    @JsonProperty("password")
    @Size(min = 5, max = 20, message
            = "Senha deve conter entre 5 a 20 digitos")
    private String password;

    @NotBlank(message = "CPF nao pode estar vazio")
    @NotNull(message = "CPF é obrigatorio")
    @JsonProperty("cpf")
    @CPF
    private String CPF;
}
