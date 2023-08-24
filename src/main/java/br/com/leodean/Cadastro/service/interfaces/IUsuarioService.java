package br.com.leodean.Cadastro.service.interfaces;

import br.com.leodean.Cadastro.domain.Usuario;
import br.com.leodean.Cadastro.domain.dto.UsuarioDTO;

public interface IUsuarioService {
    UsuarioDTO createCustomer(Usuario request);
}
