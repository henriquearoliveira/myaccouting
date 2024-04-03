package br.com.contability.business.repository;

import br.com.contability.business.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    Optional<Usuario> getByEmail(String email);

    @Query("SELECT u FROM Usuario u"
            + " JOIN u.codigoUsuarios cu"
            + " WHERE cu.codigo = ?1 AND cu.ativo = TRUE")
    Optional<Usuario> getByCodigo(String codigo);
}
