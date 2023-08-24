package br.com.leodean.Cadastro.domain.mapper;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.databaseDomain.ContaDataBase;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@Component
public class ContaMapper {

    public static ContaDTO mappToResponse(ContaDataBase contaDataBase) {
        return ContaDTO.builder()
                .account_id(contaDataBase.getAccountId())
                .agencia(Long.parseLong(contaDataBase.getAgencia()))
                .numeroConta(contaDataBase.getNumeroConta())
                .build();
    }

    public static ContaDataBase mappToDataBase(Conta request, UsuarioDataBase usuarioDataBase) {
        return ContaDataBase.builder()
                .accountId(UUID.randomUUID().toString())
                .CPFCorrentista(usuarioDataBase.getCPF())
                .agencia(request.getAgencia())
                .numeroConta(request.getConta())
                .dataRegistro(LocalDateTime.now())
                .build();
    }
}
