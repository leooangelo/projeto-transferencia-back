package br.com.leodean.Cadastro.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotBlank(message = "Nome não pode estar vazio")
    @NotNull(message = "Nome é obrigatório")
    @JsonProperty("nome")
    private String name;

    @NotBlank(message = "Telefone não pode estar vazio")
    @NotNull(message = "Telefone é obrigatório")
    @JsonProperty("telefone")
    @Size(min = 11, max = 11, message
            = "Telefone deve conter 11 digitos")
    private String cell;

    @NotBlank(message = "Email não pode estar vazio")
    @NotNull(message = "Email é obrigatorio")
    @JsonProperty("email")
    @Email
    private String email;

    @NotBlank(message = "Senha não pode estar vazio")
    @NotNull(message = "Senha é obrigatório")
    @JsonProperty("password")
    @Size(min = 5, max = 20, message
            = "Senha deve conter entre 5 a 20 digitos")
    private String password;

    @NotBlank(message = "CPF não pode estar vazio")
    @NotNull(message = "CPF é obrigatório")
    @JsonProperty("cpf")
    @CPF
    private String CPF;
}
