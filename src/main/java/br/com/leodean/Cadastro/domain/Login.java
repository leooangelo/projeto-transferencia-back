package br.com.leodean.Cadastro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {

    @NotBlank(message = "Email não pode estar vazio")
    @NotNull(message = "Email é obrigatório")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Senha não pode estar vazio")
    @NotNull(message = "Senha é obrigatório")
    @JsonProperty("password")
    private String password;
}
