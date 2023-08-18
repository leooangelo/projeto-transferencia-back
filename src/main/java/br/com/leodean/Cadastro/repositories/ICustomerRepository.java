package br.com.leodean.Cadastro.repositories;


import br.com.leodean.Cadastro.domain.databaseDomain.CustomerDataBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<CustomerDataBase, String> {
    List<CustomerDataBase> findAllByOrderByNameAsc();

    Optional<CustomerDataBase> findByEmail(String email);

    @Query("select a from CustomerDataBase a where a.email like :email")
    UserDetails findByEmailToken(String email);

    Optional<CustomerDataBase> findByCell(String cell);

    Optional<CustomerDataBase> findByCPF(String cpf);
}
