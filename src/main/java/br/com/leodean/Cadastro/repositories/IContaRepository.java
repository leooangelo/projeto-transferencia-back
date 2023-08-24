package br.com.leodean.Cadastro.repositories;

import br.com.leodean.Cadastro.domain.databaseDomain.ContaDataBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface IContaRepository extends JpaRepository<ContaDataBase, String> {

    Optional<ContaDataBase> findByAgenciaAndNumeroConta( String agencia, String numeroConta);
}
