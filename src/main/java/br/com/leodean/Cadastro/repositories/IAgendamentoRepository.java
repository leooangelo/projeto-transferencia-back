package br.com.leodean.Cadastro.repositories;

import br.com.leodean.Cadastro.domain.databaseDomain.AgendamentoDataBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAgendamentoRepository extends JpaRepository<AgendamentoDataBase, String> {
    //@Query("select a from AgendamentoDataBase a where a.agenciaOrigem like :agenciaOrigem and a.contaOrigem like :contaOrigem")
    Page<AgendamentoDataBase> findByIdPessoaOrigem(Pageable pageable, String IdPessoaOrigem);
    
}
