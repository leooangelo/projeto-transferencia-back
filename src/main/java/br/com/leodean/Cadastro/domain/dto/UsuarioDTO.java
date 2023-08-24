package br.com.leodean.Cadastro.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {

    @JsonProperty("id_cliente")
    private String customerID;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("telefone")
    private String cell;

    @JsonProperty("email")
    private String email;
}
