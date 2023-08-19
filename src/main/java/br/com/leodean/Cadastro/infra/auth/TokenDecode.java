package br.com.leodean.Cadastro.infra.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDecode {

    private String sub;
    private String iss;
    private String id;
    private String exp;
}
