package br.com.leodean.Cadastro.repositories;

import br.com.leodean.Cadastro.domain.databaseDomain.AgendamentoDataBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAgendamentoRepository extends JpaRepository<AgendamentoDataBase, String> {
}
