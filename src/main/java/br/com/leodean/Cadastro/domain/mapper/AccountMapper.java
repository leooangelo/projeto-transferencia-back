package br.com.leodean.Cadastro.domain.mapper;

import br.com.leodean.Cadastro.domain.AccountRequest;
import br.com.leodean.Cadastro.domain.databaseDomain.AccountDataBase;
import br.com.leodean.Cadastro.domain.databaseDomain.CustomerDataBase;
import br.com.leodean.Cadastro.domain.dto.AccountDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
public class AccountMapper {

    public static AccountDTO mappToResponse(AccountDataBase accountDataBase) {
        return AccountDTO.builder()
                .account_id(accountDataBase.getAccount_id())
                .agencia(Long.parseLong(accountDataBase.getAgencia()))
                .numeroConta(accountDataBase.getNumeroConta())
                .build();
    }

    public static AccountDataBase mappToDataBase(AccountRequest request, CustomerDataBase customerDataBase) {
        var conta = String.valueOf(new Random().nextInt((999999 - 1) + 1) + 1);
        while (conta.length() < 6) conta = "0" + conta;

        return AccountDataBase.builder()
                .account_id(UUID.randomUUID().toString())
                .CPFCorrentista(customerDataBase.getCPF())
                .agencia(request.getAgencia())
                .numeroConta(conta)
                .localDate(LocalDateTime.now())
                .build();
    }
}
