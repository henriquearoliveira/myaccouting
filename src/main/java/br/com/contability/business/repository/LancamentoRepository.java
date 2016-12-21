package br.com.contability.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = ?1")
	public List<Lancamento> selecionaLancamentos(Long usuario);

}
