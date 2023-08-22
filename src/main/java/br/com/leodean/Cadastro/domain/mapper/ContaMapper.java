package br.com.leodean.Cadastro.domain.mapper;

import br.com.leodean.Cadastro.domain.Conta;
import br.com.leodean.Cadastro.domain.databaseDomain.ContaDataBase;
import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import br.com.leodean.Cadastro.domain.dto.ContaDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@Component
public class ContaMapper {

    public static ContaDTO mappToResponse(ContaDataBase contaDataBase) {
        return ContaDTO.builder()
                .account_id(contaDataBase.getAccount_id())
                .agencia(Long.parseLong(contaDataBase.getAgencia()))
                .numeroConta(contaDataBase.getNumeroConta())
                .build();
    }

    public static ContaDataBase mappToDataBase(Conta request, UsuarioDataBase usuarioDataBase) {
        var conta = String.valueOf(new Random().nextInt((999999 - 1) + 1) + 1);
        while (conta.length() < 6) conta = "0" + conta;

        return ContaDataBase.builder()
                .account_id(UUID.randomUUID().toString())
                .CPFCorrentista(usuarioDataBase.getCPF())
                .agencia(request.getAgencia())
                .numeroConta(conta)
                .dataRegistro(LocalDateTime.now())
                .build();
    }
}
