package br.com.leodean.Cadastro.repositories;

import br.com.leodean.Cadastro.domain.databaseDomain.AccountDataBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IAccountRepository extends JpaRepository<AccountDataBase, String> {

}
