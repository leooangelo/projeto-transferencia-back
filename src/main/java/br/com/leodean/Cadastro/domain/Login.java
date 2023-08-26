package br.com.leodean.Cadastro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {

    @NotBlank(message = "Email nao pode estar vazio")
    @NotNull(message = "Email é obrigatorio")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Senha nao pode estar vazio")
    @NotNull(message = "Senha é obrigatorio")
    @JsonProperty("password")
    private String password;
}
