package br.com.leodean.Cadastro.repositories;


import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface IUsuarioRepository extends JpaRepository<UsuarioDataBase, String> {

    Optional<UsuarioDataBase> findByEmail(String email);

    @Query("select a from UsuarioDataBase a where a.email like :email")
    UserDetails findByEmailToken(String email);

    Optional<UsuarioDataBase> findByCell(String cell);

    UsuarioDataBase findByCustomerID(String customerID);

    Optional<UsuarioDataBase> findByCPF(String cpf);
}
