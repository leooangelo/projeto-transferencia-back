package br.com.leodean.Cadastro.domain.mapper;

import br.com.leodean.Cadastro.domain.Usuario;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.domain.dto.UsuarioDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@Component
public class UsuarioMapper {


    public static List<UsuarioDTO> mappToResponse(List<UsuarioDataBase> usuarioDatabase) {
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
        for (UsuarioDataBase usuarioDataBase : usuarioDatabase) {
            usuarioDTOList.add(UsuarioDTO.builder().customerID(usuarioDataBase.getCustomerID())
                    .name(usuarioDataBase.getName())
                    .cell(usuarioDataBase.getCell())
                    .email(usuarioDataBase.getEmail()).build());
        }
        return usuarioDTOList;
    }

    public static void customerIDRequestCreated(Usuario customer, String customerIDCreated) {
        customer.setCustomerID(customerIDCreated);
    }

    public static UsuarioDataBase mappToDataBase(Usuario request) {
        return UsuarioDataBase.builder()
                .customerID(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .cell(request.getCell())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .CPF(request.getCPF())
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public static UsuarioDTO mappToResponse(UsuarioDataBase usuarioDataBase) {
        return UsuarioDTO.builder()
                .customerID(usuarioDataBase.getCustomerID())
                .name(usuarioDataBase.getName())
                .email(usuarioDataBase.getEmail())
                .cell(usuarioDataBase.getCell())
                .build();
    }


}
