package br.com.contability.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.contability.business.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
