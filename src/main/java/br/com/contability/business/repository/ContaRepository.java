package br.com.contability.business.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

	@Query("SELECT c FROM Conta c WHERE c.usuario.id = ?1")
    List<Conta> selecionaPelo(Long idUsuario);

	@Query("SELECT c FROM Conta c WHERE c.usuario.id = ?1 AND c.id = ?2")
    Optional<Conta> getConta(Long idUsuario, Long id);

	@Query("SELECT c FROM Conta c JOIN c.lancamentos l WHERE l.id = ?1")
    Conta getPeloLancamento(Long idLancamento);

	@Query("SELECT COUNT(c) FROM Conta c WHERE c.usuario.id = ?1")
    Integer selecionaNumeroContasDo(Long idUsuario);
}
