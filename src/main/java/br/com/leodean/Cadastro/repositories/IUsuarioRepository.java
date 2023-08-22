package br.com.leodean.Cadastro.repositories;


import br.com.leodean.Cadastro.domain.databaseDomain.UsuarioDataBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioDataBase, String> {
    List<UsuarioDataBase> findAllByOrderByNameAsc();

    Optional<UsuarioDataBase> findByEmail(String email);

    @Query("select a from UsuarioDataBase a where a.email like :email")
    UserDetails findByEmailToken(String email);

    Optional<UsuarioDataBase> findByCell(String cell);

    Optional<UsuarioDataBase> findByCPF(String cpf);
}
